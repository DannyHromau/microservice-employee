package com.dannyhromau.employee.facade;

import com.dannyhromau.employee.api.dto.TokenDto;
import com.dannyhromau.employee.api.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public interface AuthFacade {

    TokenDto authorize(UserDto authenticateDto);

    boolean register(UserDto registerDto);

    TokenDto refresh(TokenDto refreshToken);
}
