package com.dannyhromau.employee.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
public class PositionDto {
    private UUID id;
    private String position;
    private String description;
    private boolean fulltime;
    private ZonedDateTime updateOn;
}
