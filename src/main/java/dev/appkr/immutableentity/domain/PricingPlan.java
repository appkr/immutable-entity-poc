package dev.appkr.immutableentity.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "pricing_plans")
@Getter
@ToString
@EqualsAndHashCode(of = {"id"})
public class PricingPlan extends AbstractAggregateRoot {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private UUID externalId;

  private Long contractId;

  @ElementCollection(fetch = FetchType.EAGER)
  @OrderBy(value = "from ASC")
  @CollectionTable(name = "pricing_plan_elements",
      joinColumns = @JoinColumn(name = "pricing_plan_id"))
  @AttributeOverrides({
      @AttributeOverride(name = "from", column = @Column(name = "distance_from")),
      @AttributeOverride(name = "to", column = @Column(name = "distance_to"))
  })
  private Set<DistanceRangePricingElement> bin = new HashSet<>();

  public static PricingPlan of (Long contractId, Set<DistanceRangePricingElement> bin) {
    return new PricingPlan(UUID.randomUUID(), contractId, bin);
  }

  protected PricingPlan() {
  }

  PricingPlan(UUID externalId, Long contractId, Set<DistanceRangePricingElement> bin) {
    this.externalId = externalId;
    this.contractId = contractId;
    this.bin = bin;
  }

  @Builder
  PricingPlan(Long id, UUID externalId, Long contractId, Set<DistanceRangePricingElement> bin) {
    this.id = id;
    this.externalId = externalId;
    this.contractId = contractId;
    this.bin = bin;
  }
}
