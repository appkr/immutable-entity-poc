package dev.appkr.immutableentity.repository;

import dev.appkr.immutableentity.domain.Contract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.util.List;

import static dev.appkr.immutableentity.Fixtures.aContract;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ContractRepositoryTest {

  @Autowired ContractRepository repository;
  Contract sut;

  @Test
  void testRetrieve() {
    List<Contract> all = repository.findAll();

    assertEquals(1, all.size());
    assertEquals(sut.getExternalId(), all.get(0).getExternalId());
  }

  @BeforeEach
  void setUp() {
    this.sut = repository.save(aContract().build());
  }
}