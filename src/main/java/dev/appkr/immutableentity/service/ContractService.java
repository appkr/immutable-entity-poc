package dev.appkr.immutableentity.service;

import dev.appkr.immutableentity.api.model.ContractDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class ContractService {

  public ContractDto createContract(ContractDto dto) {
    return null;
  }

  public ContractDto getContract(UUID contractId) {
    return null;
  }

  public ContractDto updateContract(UUID contractId, ContractDto dto) {
    return null;
  }
}
