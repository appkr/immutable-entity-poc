package dev.appkr.immutableentity.api;

import dev.appkr.immutableentity.api.model.ContractDto;
import dev.appkr.immutableentity.api.model.ContractListDto;
import dev.appkr.immutableentity.service.ContractService;
import dev.appkr.immutableentity.support.HeaderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ContractApiDelegateImpl implements ContractApiDelegate {

  private final ContractService service;

  @Override
  public ResponseEntity<ContractDto> createContract(ContractDto dto) {
    ContractDto res = service.createContract(dto);

    return ResponseEntity.created(HeaderUtils.uri("/{contractId}", res.getContractId())).body(res);
  }

  @Override
  public ResponseEntity<Void> updateContract(UUID contractId, ContractDto dto) {
    ContractDto res = service.updateContract(contractId, dto);

    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<ContractDto> getContract(UUID contractId) {
    return ResponseEntity.ok(service.getContract(contractId));
  }

  @Override
  public ResponseEntity<ContractListDto> getContractChangeLogs(UUID contractId) {
    return ResponseEntity.ok(
        new ContractListDto().data(service.getContractChangeLogs(contractId)));
  }
}
