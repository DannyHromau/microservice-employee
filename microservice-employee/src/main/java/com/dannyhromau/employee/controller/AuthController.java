package com.dannyhromau.employee.controller;

import com.dannyhromau.employee.api.dto.TokenDto;
import com.dannyhromau.employee.api.dto.UserDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/auth")
@Tag(name = "Authorization service", description = "Authorization controller")
@ApiResponse(responseCode = "200", description = "Successful operation")
@ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
@ApiResponse(responseCode = "404", description = "Not found")
@ApiResponse(responseCode = "401", description = "Unauthorized")
public interface AuthController {

    @PostMapping("/login")
    ResponseEntity<TokenDto> login(@RequestBody @NonNull UserDto authenticateDto);

    @PostMapping("/refresh")
    ResponseEntity<TokenDto> refresh(@RequestBody @NonNull TokenDto refreshToken);
}

