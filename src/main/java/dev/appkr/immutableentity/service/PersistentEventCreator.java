package dev.appkr.immutableentity.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.appkr.immutableentity.domain.PersistentEvent;
import dev.appkr.immutableentity.repository.PersistentEventRepository;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PersistentEventCreator {

  private final PersistentEventRepository repository;
  private final ObjectMapper objectMapper;

  @Transactional
  public void create(String eventType, Object source) {
    String body = "";
    try {
      body = objectMapper.writeValueAsString(source);
    } catch (IOException e) {
      log.error("Serialization failed", e);
    }
    final PersistentEvent entity = PersistentEvent.newInstance(eventType, UUID.randomUUID(), body);
    repository.save(entity);
  }
}
