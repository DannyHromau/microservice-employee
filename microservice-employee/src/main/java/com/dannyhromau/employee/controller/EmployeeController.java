package com.dannyhromau.employee.controller;

import com.dannyhromau.employee.api.dto.EmployeeDto;
import com.dannyhromau.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/all")
    ResponseEntity<List<EmployeeDto>> getEmployees() {
        log.info("call getEmployees");
        return ResponseEntity.ok(employeeService.getEmployees());
    }

    @GetMapping("{id}")
    ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable String id) {
        log.info("call getEmployeeById with id: {}, id", id);
        return ResponseEntity.ok(employeeService.getEmployeeByID(id));
    }

    @PostMapping("/create")
    ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeDto employeeDto) {
        log.info("call addEmployee: {}, employee", employeeDto);
        return ResponseEntity.ok(employeeService.addEmployee(employeeDto));
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> deleteEmployee(@PathVariable String id) {
        log.info("call deleteEmployee with id: {}, id", id);
        return ResponseEntity.ok(employeeService.deleteById(id));
    }

    @PutMapping("/update")
    ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto) {
        log.info("call addEmployee: {}, employee", employeeDto);
        return ResponseEntity.ok(employeeService.updateEmployee(employeeDto));
    }

}
