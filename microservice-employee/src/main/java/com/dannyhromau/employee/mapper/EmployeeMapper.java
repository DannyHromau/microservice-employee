package com.dannyhromau.employee.mapper;

import com.dannyhromau.employee.api.dto.EmployeeDto;
import com.dannyhromau.employee.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = PositionMapper.class)
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);
    Employee mapToEmployee(EmployeeDto employeeDto);
    EmployeeDto mapToEmployeeDto(Employee employee);
    void updateEmployeeFromDto(EmployeeDto employeeDto, @MappingTarget Employee employee);
    List<EmployeeDto> mapToListEmployeeDto(List<Employee> employeeList);
}
