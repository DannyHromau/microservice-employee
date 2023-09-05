package com.dannyhromau.employee.service;

import com.dannyhromau.employee.core.token.Token;
import com.dannyhromau.employee.model.User;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    boolean register(User user);

    Token authorize(User user);

    Token refresh(Token token);

    User checkValidData(User user);
}
