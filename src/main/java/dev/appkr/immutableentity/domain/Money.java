package dev.appkr.immutableentity.domain;

import static java.math.RoundingMode.FLOOR;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collection;
import java.util.Formattable;
import java.util.Formatter;
import java.util.Objects;
import java.util.function.Function;
import org.codehaus.jackson.annotate.JsonProperty;

public class Money implements Serializable, Comparable<Money>, Formattable {

  public static final String DEFAULT_CURRENCY_UNIT = "원";

  public static final int FLOOR_ONES_PLACE = 0;
  public static final int FLOOR_TENS_PLACE = -1;
  public static final int FLOOR_HUNDREDS_PLACE = -2;
  public static final int FLOOR_THOUSANDS_PLACE = -3;

  public static final Money ZERO = Money.won(0);

  @JsonProperty("amount")
  private final BigDecimal amount;

  public static Money won(BigDecimal amount) {
    return new Money(amount);
  }

  public static Money won(long amount) {
    return new Money(BigDecimal.valueOf(amount));
  }

  public static Money won(double amount) {
    return new Money(BigDecimal.valueOf(amount));
  }

  public static Money won(String amount) {
    return new Money(new BigDecimal(amount));
  }

  private Money(BigDecimal amount) {
    if (amount.signum() < 0) {
      throw new IllegalArgumentException("amount value must be a positive number");
    }

    this.amount = amount;
  }

  public static <T> Money sum(Collection<T> bags, Function<T, Money> monetary) {
    return bags.stream().map(bag -> monetary.apply(bag)).reduce(Money.ZERO, Money::plus);
  }

  public Money plus(Money that) {
    return new Money(this.amount.add(that.amount));
  }

  public Money minus(Money that) {
    BigDecimal newAmount = this.amount.subtract(that.amount);
    if (newAmount.signum() < 0) {
      throw new IllegalArgumentException("Right must be smaller than the left");
    }

    return new Money(newAmount);
  }

  public Money times(double multiplier) {
    return new Money(this.amount.multiply(BigDecimal.valueOf(multiplier), MathContext.DECIMAL32));
  }

  public Money divide(double divisor) {
    return new Money(this.amount.divide(BigDecimal.valueOf(divisor)));
  }

  public boolean isLessThan(Money other) {
    return amount.compareTo(other.amount) < 0;
  }

  public boolean isGreaterThanOrEqualTo(Money that) {
    return amount.compareTo(that.amount) >= 0;
  }

  public Money floor() {
    return new Money(this.amount.setScale(FLOOR_ONES_PLACE, FLOOR));
  }

  public Money floor(int scale) {
    return new Money(this.amount.setScale(scale, FLOOR));
  }

  public Long toLong() {
    return amount.longValue();
  }

  public String toMonetaryString() {
    return String.format("%,d", amount.longValue()) + DEFAULT_CURRENCY_UNIT;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }

    if (!(object instanceof Money)) {
      return false;
    }

    Money other = (Money)object;

    return Objects.equals(amount.doubleValue(), other.amount.doubleValue());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(amount);
  }

  @Override
  public String toString() {
    return amount.toString();
  }

  @Override
  public int compareTo(Money that) {
    if (that == null) {
      return 1;
    }

    if (this.equals(that)) {
      return 0;
    }

    return (this.amount.compareTo(that.amount) > 0) ? 1 : -1;
  }

  @Override
  public void formatTo(Formatter formatter, int flags, int width, int precision) {
    // 9900 -> 9,900
    formatter.format("%,d", this.amount.longValue());
  }
}