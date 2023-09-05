package com.dannyhromau.employee.api.dto;

import com.dannyhromau.employee.core.base.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto extends BaseDto {
    private String username;
    private String password;
    private String email;
    private boolean blocked;
}
