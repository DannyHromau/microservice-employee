package com.dannyhromau.employee.core.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Token {
    private String accessToken;
    private String refreshToken;
}
