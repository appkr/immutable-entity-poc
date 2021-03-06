package dev.appkr.immutableentity.domain;

import org.springframework.data.geo.Distance;

import javax.persistence.Embeddable;

@Embeddable
public class DistanceRangePricingElement {

  private Distance from;
  private Distance to;
  private Distance step;
  private Money pricePerStep;
}
