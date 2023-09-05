package com.dannyhromau.employee.facade.impl;

import com.dannyhromau.employee.api.dto.TokenDto;
import com.dannyhromau.employee.api.dto.UserDto;
import com.dannyhromau.employee.core.token.Token;
import com.dannyhromau.employee.facade.AuthFacade;
import com.dannyhromau.employee.mapper.TokenMapper;
import com.dannyhromau.employee.mapper.UserMapper;
import com.dannyhromau.employee.model.User;
import com.dannyhromau.employee.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthFacadeImpl implements AuthFacade {

    private final AuthService authService;
    private final UserMapper userMapper;
    private final TokenMapper tokenMapper;

    @Override
    public TokenDto authorize(UserDto authenticateDto) {
        User user = userMapper.mapToUser(authenticateDto);
        return tokenMapper.mapToTokenDto(authService.authorize(user));
    }

    @Override
    public boolean register(UserDto registerDto) {
        User user = userMapper.mapToUser(registerDto);
        return authService.register(user);
    }

    @Override
    public TokenDto refresh(TokenDto refreshToken) {
        Token token = tokenMapper.mapToToken(refreshToken);
        return tokenMapper.mapToTokenDto(authService.refresh(token));
    }
}
