package com.dannyhromau.employee.service;

import com.dannyhromau.employee.core.base.BaseService;
import com.dannyhromau.employee.model.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface EmployeeService extends BaseService<Employee> {
    @Override
    List<Employee> getEntities(Pageable pageable);

    @Override
    Employee getEntityById(UUID id);

    @Override
    Employee addEntity(Employee entity);

    @Override
    UUID deleteEntity(UUID id);

    @Override
    Employee updateEntity(Employee entity);

    List<Employee> getEmployeesByPositionId(Pageable page, UUID positionId);
}
