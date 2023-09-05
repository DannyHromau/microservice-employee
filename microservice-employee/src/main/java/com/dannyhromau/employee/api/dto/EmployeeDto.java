package com.dannyhromau.employee.api.dto;

import com.dannyhromau.employee.core.base.BaseDto;
import com.dannyhromau.employee.model.Position;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
public class EmployeeDto extends BaseDto {

    private String firstName;
    private String lastName;
    private UUID positionId;
    private LocalDate birthDate;
    private String phone;
    private boolean isFired;
    @JsonIgnore
    private Position position;
    private ZonedDateTime firedOn;
    private boolean isFullTime;
}
