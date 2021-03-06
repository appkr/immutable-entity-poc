package dev.appkr.immutableentity.repository.converter;

import dev.appkr.immutableentity.domain.Money;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.math.BigDecimal;

@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, BigDecimal> {

  @Override
  public BigDecimal convertToDatabaseColumn(Money attribute) {
    if (attribute == null) {
      return null;
    }
    return new BigDecimal(attribute.toString());
  }

  @Override
  public Money convertToEntityAttribute(BigDecimal dbData) {
    if (dbData == null) {
      return null;
    }
    return Money.won(dbData);
  }
}
