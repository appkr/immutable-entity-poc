package dev.appkr.immutableentity.domain.factory;

import dev.appkr.immutableentity.api.model.ContractDto;
import dev.appkr.immutableentity.api.model.ContractStatusDto;
import dev.appkr.immutableentity.api.model.EndlessDateRangeDto;
import dev.appkr.immutableentity.domain.Contract;
import dev.appkr.immutableentity.domain.ContractStatus;
import dev.appkr.immutableentity.domain.DateRange;
import dev.appkr.immutableentity.service.mapper.DateTimeMapper;

import java.util.UUID;

public class ContractFactory {

  public Contract from(ContractDto dto) {
    return Contract.builder()
        .externalId((dto.getContractId() != null) ? dto.getContractId() : UUID.randomUUID())
        .status(from(dto.getStatus()))
        .validThrough(from(dto.getValidThrough()))
        .build();
  }

  private ContractStatus from(ContractStatusDto dto) {
    return ContractStatus.valueOf(dto.getValue());
  }

  private DateRange from(EndlessDateRangeDto dto) {
    DateTimeMapper dateTimeMapper = new DateTimeMapper();
    return DateRange.builder()
        .from(dateTimeMapper.toInstant(dto.getFrom()))
        .to(dateTimeMapper.toInstant(dto.getTo().orElse(null)))
        .build();
  }
}
