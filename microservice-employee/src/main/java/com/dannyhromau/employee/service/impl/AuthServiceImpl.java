package com.dannyhromau.employee.service.impl;

import com.dannyhromau.employee.core.config.ErrorMessages;
import com.dannyhromau.employee.core.security.JwtTokenProvider;
import com.dannyhromau.employee.core.security.PasswordConfig;
import com.dannyhromau.employee.core.security.SecurityConfig;
import com.dannyhromau.employee.core.token.Token;
import com.dannyhromau.employee.exception.InvalidInputDataException;
import com.dannyhromau.employee.exception.UnAuthorizedException;
import com.dannyhromau.employee.model.User;
import com.dannyhromau.employee.repository.UserRepository;
import com.dannyhromau.employee.service.AuthService;
import com.dannyhromau.employee.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtTokenProvider tokenProvider;
    private final SecurityConfig securityConfig;
    private static final String EMAIL_PATTERN =
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private final PasswordConfig passwordConfig;

    @Override
    public boolean register(User user) {
        user = checkValidData(user);
        user.setPassword(encoder.encode(user.getPassword()));
        return userService.addEntity(user) != null;
    }

    @Override
    public Token authorize(User user) throws UnAuthorizedException {
        User authUser = userRepository.findByUsername(user.getUsername());
        if (authUser != null && !authUser.isBlocked() && encoder.matches(user.getPassword(), authUser.getPassword())) {
            String accessToken = tokenProvider.createToken(authUser.getId(), user.getEmail(), user.getUsername());
            String refreshToken = tokenProvider.refreshToken(authUser.getId());
            return new Token(accessToken, refreshToken);
        } else {
            throw new UnAuthorizedException(ErrorMessages.WRONG_AUTHENTICATION_MESSAGE.label);
        }
    }

    @Override
    public Token refresh(Token token) {
        try {
            UUID id = UUID
                    .fromString((String) securityConfig
                            .jwtDecoder()
                            .decode(token.getRefreshToken())
                            .getClaims()
                            .get("id"));
            Optional<User> userOpt = userRepository.findById(id);
            User user = userOpt.get();
            return new Token(tokenProvider.createToken(user.getId(), user.getEmail(), user.getUsername()),
                    tokenProvider.refreshToken(user.getId()));
        } catch (Exception e) {
            throw new UnAuthorizedException(ErrorMessages.WRONG_REFRESH_TOKEN_MESSAGE.label);
        }
    }

    @Override
    public User checkValidData(User user) {
        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
            throw new InvalidInputDataException(ErrorMessages.INCORRECT_DATA_MESSAGE.label);
        }
        if (!user.getEmail().isEmpty() && !user.getEmail().matches(EMAIL_PATTERN)) {
            throw new InvalidInputDataException(ErrorMessages.WRONG_EMAIL_MESSAGE.label);
        }
        if (user.getUsername().isEmpty()) {
            throw new InvalidInputDataException(ErrorMessages.EMPTY_USERNAME_MESSAGE.label);
        }
        if (user.getPassword().length() < passwordConfig.getLength()) {
            throw new InvalidInputDataException(ErrorMessages.WRONG_PASSWORD_MESSAGE.label);
        }
        return user;
    }
}
