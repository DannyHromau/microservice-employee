package com.dannyhromau.employee.core.security;

import com.nimbusds.jose.JWSAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {

    private static final String SPEC_KEY = "SecretSpecialKeyOauth2.0Jwt256Bites";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/auth/register", "/api/v1/auth/login").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout(
                        logout -> {
                            logout
                                    .logoutUrl("/api/v1/auth/logout")
                                    .logoutSuccessUrl("/")
                                    .logoutSuccessHandler(getLogoutSuccessHandler())
                                    .invalidateHttpSession(true);
                        }
                )
                .oauth2ResourceServer().jwt().decoder(jwtDecoder())
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
        return http.build();
    }

    @Bean
    protected LogoutSuccessHandler getLogoutSuccessHandler() {
        return (httpServletRequest, httpServletResponse, authentication) -> {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        };
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(
                new SecretKeySpec(SPEC_KEY.getBytes(StandardCharsets.UTF_8),
                        JWSAlgorithm.RS512.getName())).build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}