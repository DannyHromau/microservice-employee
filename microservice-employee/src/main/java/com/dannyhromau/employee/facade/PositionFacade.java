package com.dannyhromau.employee.facade;

import com.dannyhromau.employee.api.dto.PositionDto;
import com.dannyhromau.employee.core.base.BaseFacade;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface PositionFacade extends BaseFacade<PositionDto> {
    @Override
    List<PositionDto> getDtos(Pageable pageable);

    @Override
    PositionDto getDtoByID(UUID id);

    @Override
    PositionDto addDto(PositionDto dto);

    @Override
    UUID deleteDtoById(UUID id);

    @Override
    PositionDto updateDto(PositionDto dto);
}
