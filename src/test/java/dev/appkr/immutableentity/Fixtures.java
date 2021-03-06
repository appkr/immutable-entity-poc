package dev.appkr.immutableentity;

import dev.appkr.immutableentity.domain.*;
import dev.appkr.immutableentity.support.Carbon;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;

import java.util.HashSet;
import java.util.UUID;

import static dev.appkr.immutableentity.domain.DistanceRangePricingElement.DistanceRangePricingElementBuilder;
import static dev.appkr.immutableentity.domain.PricingPlan.PricingPlanBuilder;

public class Fixtures {

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
          add(aDistanceRangePricingElement().build());
          add(DistanceRangePricingElement.builder()
            .from(new Distance(2.0, Metrics.KILOMETERS))
            .to(new Distance(999.0, Metrics.KILOMETERS))
            .step(new Distance(0.1, Metrics.KILOMETERS))
            .pricePerStep(Money.won(100L))
            .build()
          );
        }});
  }

  public static DistanceRangePricingElementBuilder aDistanceRangePricingElement() {
    return DistanceRangePricingElement.builder()
        .from(new Distance(0.0, Metrics.KILOMETERS))
        .to(new Distance(1.0, Metrics.KILOMETERS))
        .step(new Distance(1.0, Metrics.KILOMETERS))
        .pricePerStep(Money.won(1000));
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
            aDistanceRangePricingElementDto(),
            new DistanceRangePricingElementDto()
              .from(new DistanceDto().value("2.0"))
              .to(new DistanceDto().value("999.0"))
                .to(new DistanceDto().value("0.1"))
              .pricePerStep(100L)
        )));
  }

  public static DistanceRangePricingElementDto aDistanceRangePricingElementDto() {
    return new DistanceRangePricingElementDto()
        .from(new DistanceDto().value("1.0"))
        .to(new DistanceDto().value("2.0"))
        .step(new DistanceDto().value("1.0"))
        .pricePerStep(1000L);
  }
}
