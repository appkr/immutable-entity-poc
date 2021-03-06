package dev.appkr.immutableentity.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.time.Instant;

@Embeddable
@Getter
@ToString
@EqualsAndHashCode
public class DateRange {

  private Instant from;
  private Instant to;

  protected DateRange() {
  }

  @Builder
  public DateRange(Instant from, Instant to) {
    this.from = from;
    this.to = to;
  }
}
