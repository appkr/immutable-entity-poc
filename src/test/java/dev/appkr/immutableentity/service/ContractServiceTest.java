package dev.appkr.immutableentity.service;

import static dev.appkr.immutableentity.Fixtures.aContractDto;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.appkr.immutableentity.api.model.ContractDto;
import dev.appkr.immutableentity.api.model.ContractStatusDto;
import dev.appkr.immutableentity.repository.ContractRepository;
import dev.appkr.immutableentity.repository.PricingPlanRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ContractServiceTest {

  Logger log = LoggerFactory.getLogger(getClass());

  @Autowired ContractService sut;
  @Autowired ContractRepository contractRepository;
  @Autowired PricingPlanRepository pricingPlanRepository;

  @Test
  @Transactional
  void testCreateContract() {
    ContractDto dto = sut.createContract(aContractDto());

    log.info("dto {}", dto);
  }

  @Test
  @Transactional
  void testGetContract() {
    ContractDto dto = sut.createContract(aContractDto());

    ContractDto res = sut.getContract(dto.getContractId());

    log.info("dto {}", res);
  }

  @Test
  @Transactional
  void testUpdateContract() {
    ContractDto dto = sut.createContract(aContractDto());

    dto.status(ContractStatusDto.EFFECTIVE);
    sut.updateContract(dto.getContractId(), dto);

    assertTrue(contractRepository.findAll().size() > 1);
    assertTrue(pricingPlanRepository.findAll().size() > 1);
    log.info("dto {}", dto);
  }

  @Test
  @Transactional
  void testBeginEffective() {
    ContractDto dto = sut.createContract(aContractDto());

    sut.beginEffective(dto.getContractId());
  }

  @Test
  @Transactional
  void testTerminate() {
    ContractDto dto = sut.createContract(aContractDto().status(ContractStatusDto.EFFECTIVE));
    log.info("dto {}", dto);

    sut.terminate(dto.getContractId());
  }

  @Test
  @Transactional
  void testIllegalStateChange() {
    ContractDto dto = sut.createContract(aContractDto());

    assertThrows(IllegalStateException.class, () -> {
      sut.terminate(dto.getContractId());
    });
  }

  @AfterEach
  void tearDown() {
    contractRepository.deleteAll();
    pricingPlanRepository.deleteAll();
  }
}
