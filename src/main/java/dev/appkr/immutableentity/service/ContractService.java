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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ContractService {

  private final ContractRepository contractRepository;
  private final PricingPlanRepository pricingPlanRepository;
  private final ContractMapper contractMapper;

  public ContractDto createContract(ContractDto dto) {
    Contract c = Contract.make(dto);
    contractRepository.save(c);

    final PricingPlan p = PricingPlan.from(c.getId(), dto);
    pricingPlanRepository.save(p);

    return contractMapper.toDto(c, p);
  }

  public ContractDto getContract(UUID contractId) {
    Contract c = findContractBy(contractId);
    PricingPlan p = findPricingPlanBy(c.getId());

    return contractMapper.toDto(c, p);
  }

  public ContractDto updateContract(UUID contractId, ContractDto dto) {
    validateContractId(contractId);

    return createContract(dto);
  }

  private void validateContractId(UUID contractId) {
    findContractBy(contractId);
  }

  public List<ContractDto> getContractChangeLogs(UUID contractId) {
    // NOTE. This makes N+1 issue
    //       So, in production code, we should fetch a collection of contracts and pricingplans
    //       and map them in application(memory)
    return contractRepository.findAllByExternalIdOrderByIdDesc(contractId).stream()
        .map(c -> {
          PricingPlan p = pricingPlanRepository.findByContractId(c.getId()).orElse(null);
          return contractMapper.toDto(c, p);
        })
        .collect(Collectors.toList());
  }

  public void beginEffective(UUID contractId) {
    findContractBy(contractId).beginEffective();
  }

  public void terminate(UUID contractId) {
    findContractBy(contractId).terminate();
  }

  Contract findContractBy(UUID contractId) {
    return contractRepository.findTopByExternalIdOrderByIdDesc(contractId)
        .orElseThrow(() -> new EntityNotFoundException());
  }

  PricingPlan findPricingPlanBy(Long contractId) {
    return pricingPlanRepository.findByContractId(contractId)
        .orElseThrow(() -> new EntityNotFoundException());
  }
}
