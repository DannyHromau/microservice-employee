package com.dannyhromau.employee.facade;

import com.dannyhromau.employee.api.dto.EmployeeDto;
import com.dannyhromau.employee.core.base.BaseFacade;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface EmployeeFacade extends BaseFacade<EmployeeDto> {
    @Override
    List<EmployeeDto> getDtos(Pageable pageable);

    @Override
    EmployeeDto getDtoByID(UUID id);

    @Override
    EmployeeDto addDto(EmployeeDto dto);

    @Override
    UUID deleteDtoById(UUID id);

    @Override
    EmployeeDto updateDto(EmployeeDto dto);

    List<EmployeeDto> getEmployeesByPositionId(Pageable page, UUID positionId);
}
