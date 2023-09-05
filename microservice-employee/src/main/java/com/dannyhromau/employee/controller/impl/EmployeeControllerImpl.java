package com.dannyhromau.employee.controller.impl;

import com.dannyhromau.employee.api.dto.EmployeeDto;
import com.dannyhromau.employee.controller.EmployeeController;
import com.dannyhromau.employee.facade.EmployeeFacade;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@RequiredArgsConstructor
public class EmployeeControllerImpl implements EmployeeController {

    private final EmployeeFacade employeeFacade;


    @Override
    public ResponseEntity<List<EmployeeDto>> getAll(Pageable page) {
        log.info("call getEmployees");
        return ResponseEntity.ok(employeeFacade.getDtos(page));
    }

    @Override
    public ResponseEntity<EmployeeDto> getById(@NonNull UUID id) {
        log.info("call getEmployeeById with id: {}", id);
        return ResponseEntity.ok(employeeFacade.getDtoByID(id));
    }

    @Override
    public ResponseEntity<EmployeeDto> create(@NonNull @RequestBody EmployeeDto employeeDto) {
        log.info("call addEmployee: {}, employee", employeeDto);
        return ResponseEntity.ok(employeeFacade.addDto(employeeDto));
    }

    @Override
    public ResponseEntity<EmployeeDto> update(@NonNull @RequestBody EmployeeDto employeeDto) {
        log.info("call updateEmployee: {}, employee", employeeDto);
        return ResponseEntity.ok(employeeFacade.updateDto(employeeDto));
    }

    @Override
    public ResponseEntity<UUID> deleteById(@NonNull UUID id) {
        log.info("call deleteEmployee with id: {}", id);
        return ResponseEntity.ok(employeeFacade.deleteDtoById(id));
    }

    @Override
    public ResponseEntity<List<EmployeeDto>> getByPosId(Pageable page, @RequestParam @NonNull UUID positionId) {
        log.info("call getByPosId with position id: {}", positionId);
        return ResponseEntity.ok(employeeFacade.getEmployeesByPositionId(page, positionId));
    }


}
