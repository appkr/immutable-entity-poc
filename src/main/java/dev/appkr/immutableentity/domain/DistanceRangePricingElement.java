package dev.appkr.immutableentity.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@ToString
@EqualsAndHashCode
public class DistanceRangePricingElement {

  private Distance from;
  private Distance to;
  private Distance step;
  private Money pricePerStep;

  protected DistanceRangePricingElement() {
  }

  @Builder
  public DistanceRangePricingElement(Distance from, Distance to, Distance step, Money pricePerStep) {
    this.from = from;
    this.to = to;
    this.step = step;
    this.pricePerStep = pricePerStep;
  }
}
