package dev.appkr.immutableentity;

import dev.appkr.immutableentity.api.model.*;
import dev.appkr.immutableentity.domain.*;
import dev.appkr.immutableentity.support.Carbon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

import static dev.appkr.immutableentity.domain.PricingPlan.PricingPlanBuilder;
import static java.util.Arrays.asList;

public class Fixtures {

  // ENTITY

  public static Contract.ContractBuilder aContract() {
    return Contract.builder()
        .externalId(UUID.randomUUID())
        .status(ContractStatus.DRAFT)
        .validThrough(thisYear().build());
  }

  public static DateRange.DateRangeBuilder thisYear() {
    return DateRange.builder()
        .from(Carbon.seoul().startOfYear().toInstant())
        .to(Carbon.seoul().endOfYear().toInstant());
  }

  public static PricingPlanBuilder aPricingPlan() {
    return PricingPlan.builder()
        .externalId(UUID.randomUUID())
        .contractId(1L)
        .bin(new HashSet<DistanceRangePricingElement>() {{
          add(DistanceRangePricingElement.builder()
              .from(Distance.ZERO)
              .to(new Distance(1.0, DistanceUnit.KILOMETER))
              .step(new Distance(1.0, DistanceUnit.KILOMETER))
              .pricePerStep(Money.won(1000)).build());
          add(DistanceRangePricingElement.builder()
            .from(new Distance(1.0, DistanceUnit.KILOMETER))
            .to(new Distance(999.0, DistanceUnit.KILOMETER))
            .step(new Distance(0.1, DistanceUnit.KILOMETER))
            .pricePerStep(Money.won(100L))
            .build());
        }});
  }

  // DTO

  public static ContractDto aContractDto() {
    UUID contractId = UUID.randomUUID();
    return new ContractDto()
        .contractId(contractId)
        .status(ContractStatusDto.DRAFT)
        .validThrough(aDateRangeDto())
        .pricingPlan(aPricingPlanDto().contractId(contractId));
  }

  public static EndlessDateRangeDto aDateRangeDto() {
    return new EndlessDateRangeDto()
        .from(Carbon.seoul().startOfYear().toOffsetDateTime())
        .to(Carbon.seoul().endOfYear().toOffsetDateTime());
  }

  public static PricingPlanDto aPricingPlanDto() {
    return new PricingPlanDto()
        .pricingPlanId(UUID.randomUUID())
        .contractId(UUID.randomUUID())
        .bin(new ArrayList<DistanceRangePricingElementDto>(asList(
            new DistanceRangePricingElementDto()
                .from(new DistanceDto().value("1.0"))
                .to(new DistanceDto().value("2.0"))
                .step(new DistanceDto().value("1.0"))
                .pricePerStep(1000L),
            new DistanceRangePricingElementDto()
              .from(new DistanceDto().value("1.0"))
              .to(new DistanceDto().value("999.0"))
              .step(new DistanceDto().value("0.1"))
              .pricePerStep(100L)
        )));
  }
}
