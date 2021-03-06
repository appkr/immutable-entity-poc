package dev.appkr.immutableentity.api;

import static dev.appkr.immutableentity.api.error.ErrorConstants.CONSTRAINT_VIOLATION_TYPE;
import static java.util.Arrays.asList;

import dev.appkr.immutableentity.api.error.BadRequestAlertException;
import dev.appkr.immutableentity.api.model.ExampleDto;
import dev.appkr.immutableentity.api.model.ExampleListDto;
import dev.appkr.immutableentity.api.model.PageDto;
import dev.appkr.immutableentity.support.SecurityUtils;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Given:
 *  UAA is working at port 9999
 *
 *  When: Login to UAA and save access_token to ACCESS_TOKEN variable
 *  ```bash
 *  $ RESPONSE=$(curl -s -X POST --data "username=user&password=user&grant_type=password&scope=openid" http://web_app:changeit@localhost:9999/oauth/token)
 *  $ ACCESS_TOKEN=$(echo $RESPONSE | jq .access_token | xargs)
 *
 * Then: Use API resources with the given ACCESS_TOKEN
 *  ```bash
 *  $ curl -s -H "Authorization: bearer ${ACCESS_TOKEN}" http://localhost:8080/api/examples
 *  {
 *    "data": [
 *      {
 *        "exampleId": 1,
 *        "title": "original title",
 *        "createdAt": "2020-12-07T20:54:50.905309+09:00",
 *        "updatedAt": "2020-12-07T20:54:50.905339+09:00",
 *        "createdBy": "user",
 *        "updatedBy": "user"
 *      }
 *    ],
 *    "page": {
 *      "size": 1,
 *      "totalElements": 1,
 *      "totalPages": 1,
 *      "number": 1
 *    }
 *  }
 *  ```
 */
@Component
public class ExampleApiDelegateImpl implements ExampleApiDelegate {

  @Override
  public ResponseEntity<ExampleListDto> listExamples(Integer page, Integer size) {
    final ExampleDto elem = new ExampleDto()
        .exampleId(1L)
        .title("original title")
        .createdAt(OffsetDateTime.now())
        .updatedAt(OffsetDateTime.now())
        .createdBy(SecurityUtils.getCurrentUserLogin().get())
        .updatedBy(SecurityUtils.getCurrentUserLogin().get());

    final ExampleListDto body = new ExampleListDto()
        .data(new ArrayList<>(asList(elem)))
        .page(new PageDto()
            .number(1)
            .size(1)
            .totalElements(1L)
            .totalPages(1)
        );

    return ResponseEntity.ok(body);
  }

  /**
   * When: Authorization header is not given
   * ```
   * $ curl -s http://localhost:8080/api/exceptions
   * {
   *   "type": "immutableentity/problem-with-message",
   *   "title": "Unauthorized",
   *   "status": 401,
   *   "detail": "Full authentication is required to access this resource",
   *   "path": "/api/exceptions",
   *   "message": "error.http.401"
   * }
   * ```
   *
   * When: type=Unacceptable is given
   * ```
   * $ curl -s -H "Authorization: bearer ${ACCESS_TOKEN}" http://localhost:8080/api/exceptions?type=Unacceptable
   * {
   *   "errorKey": "immutableentity/constraint-violation",
   *   "type": "immutableentity/problem-with-message",
   *   "title": "Unacceptable type given: Unacceptable",
   *   "status": 400,
   *   "message": "error.immutableentity/constraint-violation",
   *   "params": null
   * }
   * ```
   *
   * When: type=ServerError is given
   * ```
   * $ curl -s -H "Authorization: bearer ${ACCESS_TOKEN}" http://localhost:8080/api/exceptions?type=ServerError
   * {
   *   "type": "immutableentity/problem-with-message",
   *   "title": "Internal Server Error",
   *   "status": 500,
   *   "detail": "RuntimeException",
   *   "path": "/api/exceptions",
   *   "message": "error.http.500"
   * }
   * ```
   *
   * When: type=NoSuchElement is given
   * ```
   * $ curl -s -H "Authorization: bearer ${ACCESS_TOKEN}" http://localhost:8080/api/exceptions?type=NoSuchElement
   * {
   *   "type": "immutableentity/problem-with-message",
   *   "status": 404,
   *   "path": "/api/exceptions",
   *   "message": "NoSuchElementException"
   * }
   *
   * When: Not existing endpoint is given
   * ```
   * $ curl -s -H "Authorization: bearer ${ACCESS_TOKEN}" http://localhost:8080/api/not-existing
   * {
   *   "type": "immutableentity/problem-with-message",
   *   "title": "Not Found",
   *   "status": 404,
   *   "detail": "No handler found for GET /api/not-existing",
   *   "path": "/api/not-existing",
   *   "message": "error.http.404"
   * }
   * ```
   */
  @Override
  public ResponseEntity<Void> getException(String type) {
    switch (type) {
      case "ServerError":
        throw new RuntimeException("RuntimeException");
      case "NoSuchElement":
        throw new NoSuchElementException("NoSuchElementException");
      default:
        throw new BadRequestAlertException("Unacceptable type given: " + type,
            null, CONSTRAINT_VIOLATION_TYPE.toString());
    }
  }
}
