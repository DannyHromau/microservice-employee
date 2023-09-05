package com.dannyhromau.employee.core.config;

import com.dannyhromau.employee.api.dto.ErrorMessageDto;
import com.dannyhromau.employee.exception.DeletingEntityException;
import com.dannyhromau.employee.exception.EntityNotfoundException;
import com.dannyhromau.employee.exception.InvalidInputDataException;
import com.dannyhromau.employee.exception.UnAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler({EntityNotfoundException.class})
    protected ResponseEntity<ErrorMessageDto> notFoundHandler(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessageDto(e.getMessage()));
    }

    @ExceptionHandler({InvalidInputDataException.class,
            DataIntegrityViolationException.class, IllegalArgumentException.class})
    protected ResponseEntity<ErrorMessageDto> invalidDataHandler(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessageDto(ErrorMessages.INCORRECT_DATA_MESSAGE.label));
    }

    @ExceptionHandler({DeletingEntityException.class})
    protected ResponseEntity<ErrorMessageDto> conflictHandler(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler({UnAuthorizedException.class})
    protected ResponseEntity<ErrorMessageDto> unauthorizedHandler(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
