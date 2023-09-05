package com.dannyhromau.employee.repository;

import com.dannyhromau.employee.model.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    Employee findByLastNameAndPhone(String lastName, String phone);

    Employee findByUpdatedOnAndId(ZonedDateTime updateTime, UUID id);

    List<Employee> findByPositionId(Pageable page, UUID positionId);
}
