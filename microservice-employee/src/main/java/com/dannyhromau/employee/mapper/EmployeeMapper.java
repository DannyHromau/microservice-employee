package com.dannyhromau.employee.mapper;

import com.dannyhromau.employee.api.dto.EmployeeDto;
import com.dannyhromau.employee.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = PositionMapper.class)
public interface EmployeeMapper {

    Employee mapToEmployee(EmployeeDto employeeDto);

    EmployeeDto mapToEmployeeDto(Employee employee);

    @Mapping(target = "createdOn", ignore = true)
    void updateEmployeeFromDto(EmployeeDto employeeDto, @MappingTarget Employee employee);

    List<EmployeeDto> mapToListEmployeeDto(List<Employee> employeeList);
}
