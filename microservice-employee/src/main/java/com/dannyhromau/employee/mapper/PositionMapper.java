package com.dannyhromau.employee.mapper;

import com.dannyhromau.employee.api.dto.PositionDto;
import com.dannyhromau.employee.model.Position;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = EmployeeMapper.class)
public interface PositionMapper {

    @Mapping(target = "id", ignore = true)
    Position mapToPosition(PositionDto positionDto);

    PositionDto mapToPositionDto(Position position);

    @Mapping(target = "createdOn", ignore = true)
    void updatePositionFromDto(PositionDto positionDto, @MappingTarget Position position);

    List<PositionDto> mapToListPositionDto(List<Position> positionList);
}
