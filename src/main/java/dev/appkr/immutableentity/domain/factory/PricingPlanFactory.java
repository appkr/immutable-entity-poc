package dev.appkr.immutableentity.domain.factory;

import dev.appkr.immutableentity.api.model.ContractDto;
import dev.appkr.immutableentity.api.model.DistanceDto;
import dev.appkr.immutableentity.api.model.DistanceRangePricingElementDto;
import dev.appkr.immutableentity.domain.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toSet;

public class PricingPlanFactory {

  public PricingPlan from(Long contractId, ContractDto dto) {
    return PricingPlan.builder()
        .externalId((dto.getPricingPlan().getPricingPlanId() != null)
            ? dto.getPricingPlan().getPricingPlanId() : UUID.randomUUID())
        .contractId(contractId)
        .bin(from(dto.getPricingPlan().getBin()))
        .build();
  }

  private Set<DistanceRangePricingElement> from(List<DistanceRangePricingElementDto> elements) {
    return elements.stream()
        .map(this::from)
        .collect(toSet());
  }

  private DistanceRangePricingElement from(DistanceRangePricingElementDto dto) {
    return DistanceRangePricingElement.builder()
        .from(from(dto.getFrom()))
        .to(from(dto.getTo()))
        .step(from(dto.getStep()))
        .pricePerStep(Money.won(dto.getPricePerStep()))
        .build();
  }

  private Distance from(DistanceDto dto) {
    return new Distance(Double.valueOf(dto.getValue()), DistanceUnit.KILOMETER);
  }
}
