package com.dannyhromau.employee.api.dto;

import com.dannyhromau.employee.core.base.BaseDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PositionDto extends BaseDto {

    private String position;
    private String description;
    @JsonIgnore
    private List<EmployeeDto> employeeDtoList;
}
