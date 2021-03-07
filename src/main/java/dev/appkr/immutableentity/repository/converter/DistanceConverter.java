package dev.appkr.immutableentity.repository.converter;

import dev.appkr.immutableentity.domain.Distance;
import dev.appkr.immutableentity.domain.DistanceUnit;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DistanceConverter implements AttributeConverter<Distance, Double> {

  @Override
  public Double convertToDatabaseColumn(Distance attribute) {
    return attribute.getValue();
  }

  @Override
  public Distance convertToEntityAttribute(Double dbData) {
    return new Distance(dbData, DistanceUnit.KILOMETER);
  }
}
