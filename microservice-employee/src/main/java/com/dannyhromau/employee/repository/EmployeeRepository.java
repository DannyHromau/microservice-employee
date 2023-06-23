package com.dannyhromau.employee.repository;

import com.dannyhromau.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    Employee findByLastNameAndPhone(String lastName, String phone);

    Employee findByUpdateOn(ZonedDateTime updateTime);
}
