package com.dannyhromau.employee.config;

import com.dannyhromau.employee.api.dto.EmployeeDto;
import com.dannyhromau.employee.exception.EmployeeNotFoundException;
import com.dannyhromau.employee.exception.InvalidEmployeeDataException;
import com.dannyhromau.employee.exception.InvalidPositionDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler({EmployeeNotFoundException.class})
    protected ResponseEntity<EmployeeDto> notFoundHandler() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @ExceptionHandler({InvalidEmployeeDataException.class, InvalidPositionDataException.class})
    protected ResponseEntity<EmployeeDto> invalidDataHandler() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
