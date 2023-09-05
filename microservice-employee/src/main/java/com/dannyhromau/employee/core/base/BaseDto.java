package com.dannyhromau.employee.core.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseDto implements Serializable {

    private UUID id;
    private ZonedDateTime createdOn;
    private ZonedDateTime updatedOn;
}