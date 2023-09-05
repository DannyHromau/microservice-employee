package com.dannyhromau.employee.core.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "password")
public class PasswordConfig {
    private int length;
}
