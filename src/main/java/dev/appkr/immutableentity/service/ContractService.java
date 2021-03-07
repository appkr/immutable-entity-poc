package dev.appkr.immutableentity.service;

import dev.appkr.immutableentity.api.model.ContractDto;
import dev.appkr.immutableentity.domain.Contract;
import dev.appkr.immutableentity.domain.PricingPlan;
import dev.appkr.immutableentity.repository.ContractRepository;
import dev.appkr.immutableentity.repository.PricingPlanRepository;
import dev.appkr.immutableentity.service.mapper.ContractMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ContractService {

  private final ContractRepository contractRepository;
  private final PricingPlanRepository pricingPlanRepository;
  private final ContractMapper contractMapper;

  public ContractDto createContract(ContractDto dto) {
    Contract c = Contract.from(dto);
    contractRepository.save(c);

    final PricingPlan p = PricingPlan.from(c.getId(), dto);
    pricingPlanRepository.save(p);

    return contractMapper.toDto(c, p);
  }

  public ContractDto getContract(UUID contractId) {
    Contract c = contractRepository.findTopByExternalIdOrderByIdDesc(contractId)
        .orElseThrow(() -> new EntityNotFoundException());
    PricingPlan p = pricingPlanRepository.findByContractId(c.getId())
        .orElseThrow(() -> new EntityNotFoundException());

    return contractMapper.toDto(c, p);
  }

  public ContractDto updateContract(UUID contractId, ContractDto dto) {
    validateContractId(contractId);

    return createContract(dto);
  }

  private void validateContractId(UUID contractId) {
    Contract c = contractRepository.findTopByExternalIdOrderByIdDesc(contractId)
        .orElseThrow(() -> new EntityNotFoundException());
  }
}
