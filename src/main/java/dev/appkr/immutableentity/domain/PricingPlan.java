package dev.appkr.immutableentity.domain;

import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "pricing_plans")
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
  private Set<DistanceRangePricingElement> bin = new HashSet<>();
}
