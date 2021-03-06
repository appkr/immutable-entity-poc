package dev.appkr.immutableentity.service.mapper;

import dev.appkr.immutableentity.api.model.ExampleDto;
import dev.appkr.immutableentity.domain.Example;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DateTimeMapper.class})
public interface ExampleMapper extends EntityMapper<ExampleDto, Example>{

  @Override
  @Mapping(source = "id", target = "exampleId")
  ExampleDto toDto(Example entity);
}
