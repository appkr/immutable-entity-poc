package dev.appkr.immutableentity.domain;

import javax.persistence.Embeddable;
import java.time.Instant;

@Embeddable
public class DateRange {

  private Instant from;
  private Instant to;
}
