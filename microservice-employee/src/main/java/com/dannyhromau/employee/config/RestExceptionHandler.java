package com.dannyhromau.employee.config;

import com.dannyhromau.employee.api.dto.EmployeeDto;
import com.dannyhromau.employee.exception.EntityNotfoundException;
import com.dannyhromau.employee.exception.InvalidEmployeeDataException;
import com.dannyhromau.employee.exception.InvalidPositionDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler({EntityNotfoundException.class})
    protected ResponseEntity<EmployeeDto> notFoundHandler() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @ExceptionHandler({InvalidEmployeeDataException.class, InvalidPositionDataException.class})
    protected ResponseEntity<EmployeeDto> invalidDataHandler() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
