package com.dannyhromau.employee.facade.impl;

import com.dannyhromau.employee.api.dto.EmployeeDto;
import com.dannyhromau.employee.facade.EmployeeFacade;
import com.dannyhromau.employee.mapper.EmployeeMapper;
import com.dannyhromau.employee.model.Employee;
import com.dannyhromau.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class EmployeeFacadeImpl implements EmployeeFacade {
    private final EmployeeMapper employeeMapper;
    private final EmployeeService employeeService;

    @Override
    public List<EmployeeDto> getDtos(Pageable pageable) {
        return employeeMapper.mapToListEmployeeDto(employeeService.getEntities(pageable));
    }

    @Override
    public EmployeeDto getDtoByID(UUID id) {
        return employeeMapper.mapToEmployeeDto(employeeService.getEntityById(id));
    }

    @Override
    public EmployeeDto addDto(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.mapToEmployee(employeeDto);
        return employeeMapper.mapToEmployeeDto(employeeService.addEntity(employee));
    }

    @Override
    public UUID deleteDtoById(UUID id) {
        return employeeService.deleteEntity(id);
    }

    @Override
    public EmployeeDto updateDto(EmployeeDto employeeDto) {
        Employee employee = employeeService.getEntityById(employeeDto.getId());
        employeeMapper.updateEmployeeFromDto(employeeDto, employee);
        return employeeMapper.mapToEmployeeDto(employeeService.updateEntity(employee));
    }


    public List<EmployeeDto> getEmployeesByPositionId(Pageable page, UUID positionId) {
        return employeeMapper.mapToListEmployeeDto(employeeService.getEmployeesByPositionId(page, positionId));
    }
}
