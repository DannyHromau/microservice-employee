package com.dannyhromau.test.api.auth;

import com.dannyhromau.employee.Application;
import com.dannyhromau.employee.api.dto.TokenDto;
import com.dannyhromau.employee.api.dto.UserDto;
import com.dannyhromau.employee.controller.AuthController;
import com.dannyhromau.employee.core.security.SecurityConfig;
import com.dannyhromau.employee.facade.AuthFacade;
import com.dannyhromau.employee.repository.PositionRepository;
import com.dannyhromau.employee.repository.UserRepository;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.context.TestPropertySource;

import java.util.Objects;

import static io.restassured.RestAssured.given;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.yml")
public class AuthControllerTest {


    @LocalServerPort
    private int port;
    @Autowired
    private AuthController authController;
    @Autowired
    private AuthFacade authFacade;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private SecurityConfig securityConfig;
    @Value("${host}")
    private String host;
    @Value("${authMapping}")
    private String requestMapping;


    @BeforeEach
    public void setup() {
        UserDto userDto = new UserDto();
        userDto.setUsername("user");
        userDto.setEmail("user@user.com");
        userDto.setPassword("11111111");
        authFacade.register(userDto);
    }

    @AfterEach
    public void delete() {
        userRepository.deleteAll();
        positionRepository.deleteAll();

    }

    @Test
    void login_when_registered_user_test() {
        UserDto userDto = new UserDto();
        userDto.setUsername("user");
        userDto.setEmail("user@user.com");
        userDto.setPassword("11111111");
        Response response = given()
                .port(port)
                .body(userDto)
                .when()
                .contentType(ContentType.JSON)
                .post(host + port + requestMapping + "/login")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .extract().response();
        TokenDto actualToken = response.as(TokenDto.class);
        Jwt actualJwtToken = securityConfig.jwtDecoder().decode(actualToken.getAccessToken());
        Assertions.assertEquals(userDto.getEmail(), actualJwtToken.getClaims().get("email"));
        Assertions.assertEquals(userDto.getUsername(), actualJwtToken.getClaims().get("username"));
    }

    @Test
    void login_when_unregistered_user_test() {
        UserDto userDto = new UserDto();
        userDto.setUsername("test");
        userDto.setEmail("user@user.com");
        userDto.setPassword("22222222");
        given()
                .port(port)
                .body(userDto)
                .when()
                .contentType(ContentType.JSON)
                .post(host + port + requestMapping + "/login")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(401)
                .extract().response();
    }

    @Test
    void login_when_empty_user_data_test() {
        UserDto userDto = new UserDto();
        userDto.setUsername("");
        userDto.setEmail("");
        userDto.setPassword("");
        given()
                .port(port)
                .body(userDto)
                .when()
                .contentType(ContentType.JSON)
                .post(host + port + requestMapping + "/login")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(401)
                .extract().response();
    }

    @Test
    void refresh_when_valid_token_test() {
        UserDto userDto = new UserDto();
        userDto.setUsername("user");
        userDto.setEmail("user@user.com");
        userDto.setPassword("11111111");
        String accessToken = Objects.requireNonNull(authController.login(userDto).getBody()).getAccessToken();
        TokenDto token = Objects.requireNonNull(authController.login(userDto).getBody());
        Response response = given()
                .port(port)
                .auth()
                .oauth2(accessToken)
                .body(token)
                .when()
                .contentType(ContentType.JSON)
                .post(host + port + requestMapping + "/refresh")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .extract().response();
        TokenDto actualToken = response.as(TokenDto.class);
        Jwt actualJwtToken = securityConfig.jwtDecoder().decode(actualToken.getAccessToken());
        Assertions.assertEquals(userDto.getEmail(), actualJwtToken.getClaims().get("email"));
        Assertions.assertEquals(userDto.getUsername(), actualJwtToken.getClaims().get("username"));
    }

    @Test
    void refresh_when_invalid_refresh_token_test() {
        UserDto userDto = new UserDto();
        userDto.setUsername("user");
        userDto.setEmail("user@user.com");
        userDto.setPassword("11111111");
        String accessToken = Objects.requireNonNull(authController.login(userDto).getBody()).getAccessToken();
        TokenDto token = Objects.requireNonNull(authController.login(userDto).getBody());
        char oldChar = token.getRefreshToken().charAt(1);
        char newChar = oldChar == 'a' ? 'b' : 'a';
        token.setRefreshToken(token.getRefreshToken().replace(oldChar, newChar));
        given()
                .port(port)
                .auth()
                .oauth2(accessToken)
                .body(token)
                .when()
                .contentType(ContentType.JSON)
                .post(host + port + requestMapping + "/refresh")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(401)
                .extract().response();
    }
}

