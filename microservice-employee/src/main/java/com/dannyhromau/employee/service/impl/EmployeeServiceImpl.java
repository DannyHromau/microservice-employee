package com.dannyhromau.employee.service.impl;

import com.dannyhromau.employee.core.config.ErrorMessages;
import com.dannyhromau.employee.exception.DeletingEntityException;
import com.dannyhromau.employee.exception.EntityNotfoundException;
import com.dannyhromau.employee.exception.InvalidInputDataException;
import com.dannyhromau.employee.model.Employee;
import com.dannyhromau.employee.model.Position;
import com.dannyhromau.employee.repository.EmployeeRepository;
import com.dannyhromau.employee.service.EmployeeService;
import com.dannyhromau.employee.service.PositionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
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
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PositionService positionService;


    @Override
    public List<Employee> getEntities(Pageable pageable) {
        return employeeRepository.findAll(pageable).toList();
    }

    @Override
    public Employee getEntityById(UUID id) {
        return checkValidData(id);
    }

    @Override
    public Employee addEntity(Employee employee) {
        Position position = positionService.getEntityById(employee.getPositionId());
        employee.setFired(false);
        employee.setPosition(position);
        employee.setRegDate(ZonedDateTime.now());
        employee.setCreatedOn(ZonedDateTime.now());
        employeeRepository.save(employee);
        employee = employeeRepository.findByLastNameAndPhone(employee.getLastName(), employee.getPhone());
        if (employee == null) {
            throw new InvalidInputDataException(ErrorMessages.CREATING_DATA_ERROR_MESSAGE.label);
        }
        return employee;
    }


    @Override
    public UUID deleteEntity(UUID id) {
        Employee employee = checkValidData(id);
        employeeRepository.deleteById(employee.getId());
        if (employeeRepository.findByLastNameAndPhone(employee.getLastName(), employee.getPhone()) != null) {
            throw new DeletingEntityException(ErrorMessages.DELETING_DATA_ERROR_MESSAGE.label);
        }
        return id;
    }

    @Override
    public Employee updateEntity(Employee employee) {
        ZonedDateTime updateTime = ZonedDateTime.now();
        employee.setUpdatedOn(updateTime);
        employeeRepository.save(employee);
        if (employeeRepository.findByUpdatedOnAndId(updateTime, employee.getId()) == null) {
            throw new InvalidInputDataException(ErrorMessages.UPDATING_DATA_ERROR_MESSAGE.label);
        }
        return employee;
    }

    public List<Employee> getEmployeesByPositionId(Pageable page, UUID positionId) {
        return employeeRepository.findByPositionId(page, positionId);
    }

    private Employee checkValidData(@NonNull UUID id) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isEmpty()) {
            throw new EntityNotfoundException(String.format(ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label, id));
        } else {
            return employeeOpt.get();
        }
    }


}
