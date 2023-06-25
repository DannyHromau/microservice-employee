package com.dannyhromau.employee.mapper;

import com.dannyhromau.employee.api.dto.PositionDto;
import com.dannyhromau.employee.model.Position;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = EmployeeMapper.class)
public interface PositionMapper {
    PositionMapper INSTANCE = Mappers.getMapper(PositionMapper.class);
    Position mapToPosition(PositionDto positionDto);
    PositionDto mapToPositionDto(Position position);
    void updatePositionFromDto(PositionDto positionDto, @MappingTarget Position position);
    List<PositionDto> mapToListPositionDto(List<Position> positionList);
}
