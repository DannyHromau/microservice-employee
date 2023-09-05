package com.dannyhromau.employee.mapper;

import com.dannyhromau.employee.api.dto.TokenDto;
import com.dannyhromau.employee.core.token.Token;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenMapper {
    Token mapToToken(TokenDto tokenDto);

    TokenDto mapToTokenDto(Token token);
}
