package com.dannyhromau.employee.facade.impl;

import com.dannyhromau.employee.api.dto.PositionDto;
import com.dannyhromau.employee.facade.PositionFacade;
import com.dannyhromau.employee.mapper.PositionMapper;
import com.dannyhromau.employee.model.Position;
import com.dannyhromau.employee.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class PositionFacadeImpl implements PositionFacade {

    private final PositionService positionService;
    private final PositionMapper positionMapper;

    @Override
    public List<PositionDto> getDtos(Pageable pageable) {
        return positionMapper.mapToListPositionDto(positionService.getEntities(pageable));
    }

    @Override
    public PositionDto getDtoByID(UUID id) {
        return positionMapper.mapToPositionDto(positionService.getEntityById(id));
    }

    @Override
    public PositionDto addDto(PositionDto dto) {
        Position position = positionMapper.mapToPosition(dto);
        return positionMapper.mapToPositionDto(positionService.addEntity(position));
    }

    @Override
    public UUID deleteDtoById(UUID id) {
        return positionService.deleteEntity(id);
    }

    @Override
    public PositionDto updateDto(PositionDto dto) {
        Position position = positionService.getEntityById(dto.getId());
        positionMapper.updatePositionFromDto(dto, position);
        return positionMapper.mapToPositionDto(positionService.updateEntity(position));
    }

}
