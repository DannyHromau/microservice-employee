package com.dannyhromau.employee.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;
@Getter
@Setter
public class EmployeeDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private UUID positionId;
    private LocalDate birthDate;
    private String phone;
    private ZonedDateTime regDate;
    private ZonedDateTime updateOn;
    private boolean isFired;
    private ZonedDateTime firedOn;
}
