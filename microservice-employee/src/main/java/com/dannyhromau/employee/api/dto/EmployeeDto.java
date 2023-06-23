package com.dannyhromau.employee.api.dto;

import com.dannyhromau.employee.model.Position;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;
@Getter
@Setter
public class EmployeeDto {
    @NonNull
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phone;
    private ZonedDateTime regDate;
    private ZonedDateTime updateOn;
    private boolean isFired;
    private ZonedDateTime firedOn;
    private Position position;
}
