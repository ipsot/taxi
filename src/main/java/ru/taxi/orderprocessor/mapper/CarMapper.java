package ru.taxi.orderprocessor.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.taxi.orderprocessor.dto.CarDTO;
import ru.taxi.orderprocessor.dto.CarCreatUpdateOperationDTO;
import ru.taxi.orderprocessor.entity.CarEntity;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CarMapper {

    @Mapping(target = "priorityClass",ignore = true)
    CarEntity dtoToEntity(CarCreatUpdateOperationDTO carCreatUpdateOperationDTO);

    CarDTO entityToDto(CarEntity carEntity);

    CarEntity updateFromDto(CarCreatUpdateOperationDTO source,@MappingTarget CarEntity target);

    List<CarDTO> entityToDto(Collection<CarEntity> target);

}
