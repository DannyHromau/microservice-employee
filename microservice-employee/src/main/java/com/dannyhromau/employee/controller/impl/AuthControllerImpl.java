package com.dannyhromau.employee.controller.impl;

import com.dannyhromau.employee.api.dto.TokenDto;
import com.dannyhromau.employee.api.dto.UserDto;
import com.dannyhromau.employee.controller.AuthController;
import com.dannyhromau.employee.facade.AuthFacade;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthControllerImpl implements AuthController {
    private final AuthFacade authFacade;

    @Override
    public ResponseEntity<TokenDto> login(@RequestBody @NonNull UserDto authenticateDto) {
        log.info("call login");
        return ResponseEntity.ok(authFacade.authorize(authenticateDto));
    }

    @Override
    public ResponseEntity<TokenDto> refresh(@RequestBody @NonNull TokenDto refreshToken) {
        log.info("call refresh token");
        return ResponseEntity.ok(authFacade.refresh(refreshToken));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Boolean> register(@RequestBody @NonNull UserDto userDto) {
        log.info("call register user");
        return ResponseEntity.ok(authFacade.register(userDto));
    }
}
