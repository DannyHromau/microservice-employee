package com.dannyhromau.employee.service;

import com.dannyhromau.employee.api.dto.EmployeeDto;
import com.dannyhromau.employee.exception.DeletingEmployeeException;
import com.dannyhromau.employee.exception.EmployeeNotFoundException;
import com.dannyhromau.employee.exception.InvalidEmployeeDataException;
import com.dannyhromau.employee.exception.InvalidPositionDataException;
import com.dannyhromau.employee.mapper.EmployeeMapper;
import com.dannyhromau.employee.model.Employee;
import com.dannyhromau.employee.model.Position;
import com.dannyhromau.employee.repository.EmployeeRepository;
import com.dannyhromau.employee.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final EmployeeMapper employeeMapper;

    public List<EmployeeDto> getEmployees() {
        return employeeMapper.mapToListEmployeeDto(employeeRepository.findAll());
    }

    public EmployeeDto getEmployeeByID(String id) {
        Optional<Employee> employee = employeeRepository.findById(UUID.fromString(id));
        checkExistingEmployee(id, employee);
        return employeeMapper.mapToEmployeeDto(employee.get());
    }

    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.mapToEmployee(employeeDto);
        UUID positionId = employeeDto.getPosition().getId();
        employee.setFired(false);
        Optional<Position> position = positionRepository.findById(positionId);
        if (position.isEmpty()){
            throw new InvalidPositionDataException(String.format("Employee with id : %s not exists", positionId));
        }
        employee.setPosition(position.get());
        employee.setRegDate(ZonedDateTime.now());
        employeeRepository.save(employee);
        employee = employeeRepository.findByLastNameAndPhone(employeeDto.getLastName(), employeeDto.getPhone());
        if (employee == null) {
            throw new InvalidEmployeeDataException("Unable to create database entry");
        }
        return employeeMapper.mapToEmployeeDto(employee);
    }

    public String deleteById(String id) {
        Optional<Employee> employee = employeeRepository.findById(UUID.fromString(id));
        checkExistingEmployee(id, employee);
        employeeRepository.deleteById(UUID.fromString(id));
        employee = employeeRepository.findById(UUID.fromString(id));
        if (employee.isPresent()) {
            throw new DeletingEmployeeException("Deleting data is failed");
        }
        return id;
    }

    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeDto.getId());
        checkExistingEmployee(employeeDto.getId().toString(), employeeOpt);
        Employee employee = employeeMapper.mapToEmployee(employeeDto);
        ZonedDateTime updateTime = ZonedDateTime.now();
        employee.setUpdateOn(updateTime);
        employeeRepository.save(employee);
        if (employeeRepository.findByUpdateOn(updateTime) == null) {
            throw new InvalidEmployeeDataException("Unable to update database entry");
        }
        return employeeDto;
    }

    private static void checkExistingEmployee(String id, Optional<Employee> employeeOpt) {
        if (employeeOpt.isEmpty()) {
            throw new EmployeeNotFoundException(String.format("Employee with id : %s not exists", id));
        }
    }

}
