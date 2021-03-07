package dev.appkr.immutableentity.service.mapper;

import dev.appkr.immutableentity.api.model.ContractDto;
import dev.appkr.immutableentity.api.model.ContractStatusDto;
import dev.appkr.immutableentity.api.model.EndlessDateRangeDto;
import dev.appkr.immutableentity.domain.Contract;
import dev.appkr.immutableentity.domain.ContractStatus;
import dev.appkr.immutableentity.domain.DateRange;
import dev.appkr.immutableentity.domain.PricingPlan;
import lombok.RequiredArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContractMapper {

  private final PricingPlanMapper pricingPlanMapper;
  private final DateTimeMapper dateTimeMapper;

  public ContractDto toDto(Contract c, PricingPlan p) {
    return new ContractDto()
        .contractId(c.getExternalId())
        .status(toDto(c.getStatus()))
        .validThrough(toDto(c.getValidThrough()))
        .pricingPlan(pricingPlanMapper.toDto(c.getExternalId(), p));
  }

  private ContractStatusDto toDto(ContractStatus entity) {
    return ContractStatusDto.valueOf(entity.name());
  }

  private EndlessDateRangeDto toDto(DateRange entity) {
    return new EndlessDateRangeDto()
        .from(dateTimeMapper.toOffsetDateTime(entity.getFrom()))
        .to(dateTimeMapper.toOffsetDateTime(entity.getTo()));
  }
}
