package dev.appkr.immutableentity.repository;

import dev.appkr.immutableentity.domain.PricingPlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static dev.appkr.immutableentity.Fixtures.aPricingPlan;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class PricingPlanRepositoryTest {

  @Autowired PricingPlanRepository repository;
  PricingPlan sut;

  @Test
  void testRetrieve() {
    List<PricingPlan> all = repository.findAll();

    assertEquals(1, all.size());
    assertEquals(sut.getExternalId(), all.get(0).getExternalId());
  }

  @BeforeEach
  void setUp() {
    this.sut = repository.save(aPricingPlan().build());
  }
}