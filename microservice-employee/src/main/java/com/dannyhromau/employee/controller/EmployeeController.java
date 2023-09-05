package com.dannyhromau.employee.controller;

import com.dannyhromau.employee.api.dto.EmployeeDto;
import com.dannyhromau.employee.core.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/employee")
@Tag(name = "Employee service", description = "Employee controller")
@ApiResponse(responseCode = "200", description = "Successful operation")
@ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
@ApiResponse(responseCode = "404", description = "Not found")
@ApiResponse(responseCode = "401", description = "Unauthorized")
public interface EmployeeController extends BaseController<EmployeeDto> {
    @Override
    @Operation(description = "Getting list of employees")
    ResponseEntity<List<EmployeeDto>> getAll(Pageable page);

    @Override
    @GetMapping(value = "/{id}")
    @Operation(description = "Getting employee by id")
    ResponseEntity<EmployeeDto> getById(@PathVariable @NonNull UUID id);

    @Override
    @PostMapping(value = "create")
    @Operation(description = "Creating employee")
    ResponseEntity<EmployeeDto> create(@RequestBody @NonNull EmployeeDto dto);

    @Override
    @PutMapping(value = "update")
    @Operation(description = "Updating employee")
    ResponseEntity<EmployeeDto> update(@RequestBody @NonNull EmployeeDto dto);

    @Override
    @DeleteMapping(value = "/{id}")
    @Operation(description = "Deleting employee")
    ResponseEntity<UUID> deleteById(@PathVariable @NonNull UUID id);

    @GetMapping(value = "/group")
    @Operation(description = "Getting list of employees by position")
    ResponseEntity<List<EmployeeDto>> getByPosId(Pageable page, @RequestParam @NonNull UUID positionId);
}
