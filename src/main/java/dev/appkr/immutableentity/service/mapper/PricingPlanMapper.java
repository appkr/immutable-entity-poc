package dev.appkr.immutableentity.service.mapper;

import dev.appkr.immutableentity.api.model.DistanceDto;
import dev.appkr.immutableentity.api.model.DistanceRangePricingElementDto;
import dev.appkr.immutableentity.api.model.PricingPlanDto;
import dev.appkr.immutableentity.domain.Distance;
import dev.appkr.immutableentity.domain.DistanceRangePricingElement;
import dev.appkr.immutableentity.domain.PricingPlan;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Component
public class PricingPlanMapper {

  public PricingPlanDto toDto(UUID contractId, PricingPlan entity) {
    return new PricingPlanDto()
        .pricingPlanId(entity.getExternalId())
        .contractId(contractId)
        .bin(toDto(entity.getBin()));
  }

  private List<DistanceRangePricingElementDto> toDto(Set<DistanceRangePricingElement> elements) {
    return elements.stream()
        .map(this::toDto)
        .collect(toList());
  }

  private DistanceRangePricingElementDto toDto(DistanceRangePricingElement entity) {
    return new DistanceRangePricingElementDto()
        .from(toDto(entity.getFrom()))
        .to(toDto(entity.getTo()))
        .step(toDto(entity.getStep()))
        .pricePerStep(entity.getPricePerStep().toLong());
  }

  private DistanceDto toDto(Distance entity) {
    return new DistanceDto().value(String.valueOf(entity.getValue()));
  }
}
