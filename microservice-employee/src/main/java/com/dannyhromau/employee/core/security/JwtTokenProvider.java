package com.dannyhromau.employee.core.security;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.ZonedDateTime;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    private static final String SPEC_KEY = "SecretSpecialKeyOauth2.0Jwt256Bites";

    public JwtEncoder jwtEncoder() {
        SecretKey key = new SecretKeySpec(SPEC_KEY.getBytes(), "HmacSHA256");
        JWKSource<SecurityContext> immutableSecret = new ImmutableSecret<>(key);
        return new NimbusJwtEncoder(immutableSecret);
    }

    public String createToken(UUID userId, String email, String username) {
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .claim("id", userId.toString())
                .claim("email", email)
                .claim("username", username)
                .expiresAt(ZonedDateTime.now().plusMinutes(15).toInstant())
                .build();
        JwsAlgorithm jwsAlgorithm = JWSAlgorithm.HS256::getName;

        return jwtEncoder()
                .encode(JwtEncoderParameters.from(JwsHeader.with(jwsAlgorithm).build(), jwtClaimsSet))
                .getTokenValue();

    }

    public String refreshToken(UUID userId) {
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .claim("id", userId.toString())
                .expiresAt(ZonedDateTime.now().plusHours(3).toInstant())
                .build();
        JwsAlgorithm jwsAlgorithm = JWSAlgorithm.HS256::getName;

        return jwtEncoder()
                .encode(JwtEncoderParameters.from(JwsHeader.with(jwsAlgorithm).build(), jwtClaimsSet))
                .getTokenValue();
    }

}