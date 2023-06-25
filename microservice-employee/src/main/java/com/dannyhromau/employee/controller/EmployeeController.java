package com.dannyhromau.employee.controller;

import com.dannyhromau.employee.api.dto.EmployeeDto;
import com.dannyhromau.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")
@Tag(name = "Employee service", description = "Employee controller")
@ApiResponse(responseCode = "200", description = "Successful operation")
@ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
@ApiResponse(responseCode = "404", description = "Not found")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/all")
    @Operation(description = "Getting list of employees")
    ResponseEntity<List<EmployeeDto>> getEmployees() {
        log.info("call getEmployees");
        return ResponseEntity.ok(employeeService.getEmployees());
    }

    @GetMapping("{id}")
    @Operation(description = "Getting employee by id")
    ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable UUID id) {
        log.info("call getEmployeeById with id: {}, id", id);
        return ResponseEntity.ok(employeeService.getEmployeeByID(id));
    }

    @PostMapping("/create")
    @Operation(description = "Creating employee")
    ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeDto employeeDto) {
        log.info("call addEmployee: {}, employee", employeeDto);
        return ResponseEntity.ok(employeeService.addEmployee(employeeDto));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(description = "Deleting employee")
    ResponseEntity<UUID> deleteEmployee(@PathVariable UUID id) {
        log.info("call deleteEmployee with id: {}, id", id);
        return ResponseEntity.ok(employeeService.deleteById(id));
    }

    @PutMapping("/update")
    @Operation(description = "Updating employee")
    ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto) {
        log.info("call updateEmployee: {}, employee", employeeDto);
        return ResponseEntity.ok(employeeService.updateEmployee(employeeDto));
    }

}
