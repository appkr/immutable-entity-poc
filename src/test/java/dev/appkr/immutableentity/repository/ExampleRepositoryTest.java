package dev.appkr.immutableentity.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.appkr.immutableentity.domain.Example;
import dev.appkr.immutableentity.domain.Example_;
import dev.appkr.immutableentity.repository.spec.SpecificationBuilder;
import dev.appkr.immutableentity.service.dto.SearchCriteria.Operator;
import dev.appkr.immutableentity.support.PaginationUtils;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ExampleRepositoryTest {

  @Autowired
  private ExampleRepository repository;
  private Example base;

  @BeforeEach
  @Transactional
  public void setup() {
    Example example = Example.newInstance("original title");
    this.base = repository.saveAndFlush(example);
  }

  @Test
  @Transactional(readOnly = true)
  public void testCreateRead() {
    // when
    final List<Example> all = repository.findAll();

    // then
    assertTrue(all.size() > 0);
  }

  @Test
  @Transactional(readOnly = true)
  public void testSpecification() {
    // when
    SpecificationBuilder<Example> builder = new SpecificationBuilder<>();
    final Specification<Example> spec = builder.with(Example_.TITLE, Operator.LIKE, "original").build();
    final Page<Example> page = repository.findAll(spec, PaginationUtils.getPageable());

    // then
    assertEquals(1, page.getTotalElements());
  }

  @Test
  @Transactional(readOnly = true)
  public void testQueryDsl() {
    // when
    final List<Example> all = repository.findCreatedToday();

    // then
    assertTrue(all.size() > 0);
  }
}
