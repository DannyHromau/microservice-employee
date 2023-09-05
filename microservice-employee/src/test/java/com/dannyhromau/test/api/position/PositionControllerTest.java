package com.dannyhromau.test.api.position;

import com.dannyhromau.employee.Application;
import com.dannyhromau.employee.api.dto.PositionDto;
import com.dannyhromau.employee.api.dto.UserDto;
import com.dannyhromau.employee.controller.AuthController;
import com.dannyhromau.employee.facade.AuthFacade;
import com.dannyhromau.employee.model.Position;
import com.dannyhromau.employee.repository.PositionRepository;
import com.dannyhromau.employee.repository.UserRepository;
import com.dannyhromau.employee.service.PositionService;
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
import org.springframework.test.context.TestPropertySource;

import java.util.Objects;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.yml")
public class PositionControllerTest {

    @LocalServerPort
    private int port;
    private static String token;
    @Autowired
    private AuthController authController;
    @Autowired
    private AuthFacade authFacade;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private PositionService positionService;
    @Value("${host}")
    private String host;
    @Value("${positionMapping}")
    private String requestMapping;


    @BeforeEach
    public void setup() {
        UserDto userDto = new UserDto();
        userDto.setUsername("user");
        userDto.setEmail("user@user.com");
        userDto.setPassword("11111111");
        authFacade.register(userDto);
        token = Objects.requireNonNull(authController.login(userDto).getBody()).getAccessToken();
    }

    @AfterEach
    public void delete() {
        userRepository.deleteAll();
        positionRepository.deleteAll();

    }

    @Test
    void check_non_empty_position_list_test() {
        int expectedListSize = 3;
        for (int i = 0; i < expectedListSize; i++) {
            Position position = new Position();
            position.setDescription("position" + i);
            position.setPosition("position" + i);
            positionService.addEntity(position);
        }
        Response response = given()
                .port(port)
                .auth()
                .oauth2(token)
                .when()
                .contentType(ContentType.JSON)
                .get(host + port + requestMapping + "/all")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .extract().response();
        Assertions.assertEquals(expectedListSize, response.jsonPath()
                .getList("$").size());
    }


    @Test
    void get_position_when_valid_id_test() {
        Position position = new Position();
        position.setPosition("position");
        position.setDescription("position");
        UUID id = positionService.addEntity(position).getId();
        Response response = given()
                .port(port)
                .auth()
                .oauth2(token)
                .when()
                .baseUri(host + port + requestMapping + "/")
                .basePath("{id}")
                .pathParam("id", id.toString())
                .contentType(ContentType.JSON)
                .get()
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .extract().response();
        PositionDto actualPositionDto = response.as(PositionDto.class);
        Assertions.assertEquals(position.getPosition(), actualPositionDto.getPosition());
        Assertions.assertEquals(position.getDescription(), actualPositionDto.getDescription());
    }

    @Test
    void get_position_when_id_not_exists_test() {
        Position position = new Position();
        position.setPosition("position");
        position.setDescription("position");
        UUID id = positionService.addEntity(position).getId();
        char oldChar = id.toString().charAt(1);
        char newChar = oldChar == 'a' ? 'b' : 'a';
        String invalidId = id.toString().replace(oldChar, newChar);
        given()
                .port(port)
                .auth()
                .oauth2(token)
                .when()
                .baseUri(host + port + requestMapping + "/")
                .basePath("{id}")
                .pathParam("id", invalidId)
                .contentType(ContentType.JSON)
                .get()
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(404)
                .extract().response();
    }

    @Test
    void get_position_when_empty_id_test() {
        Position position = new Position();
        position.setPosition("position");
        position.setDescription("position");
        positionService.addEntity(position);
        String invalidId = "";
        given()
                .port(port)
                .auth()
                .oauth2(token)
                .when()
                .baseUri(host + port + requestMapping + "/")
                .basePath("{id}")
                .pathParam("id", invalidId)
                .contentType(ContentType.JSON)
                .get()
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(404)
                .extract().response();
    }

    @Test
    void get_position_when_invalid_format_id_test() {
        Position position = new Position();
        position.setPosition("position");
        position.setDescription("position");
        positionService.addEntity(position);
        String invalidId = "Bad uuid";
        given()
                .port(port)
                .auth()
                .oauth2(token)
                .when()
                .baseUri(host + port + requestMapping + "/")
                .basePath("{id}")
                .pathParam("id", invalidId)
                .contentType(ContentType.JSON)
                .get()
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(400)
                .extract().response();
    }

    @Test
    void create_position_when_valid_dto_test() {
        PositionDto positionDto = new PositionDto();
        positionDto.setPosition("position");
        positionDto.setDescription("position");
        Response response = given()
                .port(port)
                .auth()
                .oauth2(token)
                .body(positionDto)
                .when()
                .contentType(ContentType.JSON)
                .post(host + port + requestMapping + "/create")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .extract().response();
        PositionDto actualPosition = response.as(PositionDto.class);
        Assertions.assertEquals(positionDto.getPosition(), actualPosition.getPosition());
        Assertions.assertEquals(positionDto.getDescription(), actualPosition.getDescription());
    }

    @Test
    void create_position_when_existing_dto_test() {
        Position position = new Position();
        position.setPosition("position");
        position.setDescription("position");
        positionService.addEntity(position);
        PositionDto positionDto = new PositionDto();
        positionDto.setDescription("position");
        positionDto.setDescription("position");
        given()
                .port(port)
                .auth()
                .oauth2(token)
                .body(positionDto)
                .when()
                .contentType(ContentType.JSON)
                .post(host + port + requestMapping + "/create")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(400)
                .extract().response();
    }

    @Test
    void create_position_when_invalid_dto_test() {
        PositionDto positionDto = new PositionDto();
        given()
                .port(port)
                .auth()
                .oauth2(token)
                .body(positionDto)
                .when()
                .contentType(ContentType.JSON)
                .post(host + port + requestMapping + "/create")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(400)
                .extract().response();
    }

    @Test
    void delete_position_when_valid_id_test() {
        Position position = new Position();
        position.setPosition("position");
        position.setDescription("position");
        position = positionService.addEntity(position);
        UUID id = position.getId();
        Response response = given()
                .port(port)
                .auth()
                .oauth2(token)
                .when()
                .contentType(ContentType.JSON)
                .baseUri(host + port + requestMapping + "/")
                .basePath("{id}")
                .pathParam("id", id.toString())
                .delete()
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .extract().response();
        String actualId = response.as(String.class);
        Assertions.assertEquals(id.toString(), actualId);
    }

    @Test
    void delete_position_when_id_not_exists_test() {
        Position position = new Position();
        position.setPosition("position");
        position.setDescription("position");
        UUID id = positionService.addEntity(position).getId();
        char oldChar = id.toString().charAt(1);
        char newChar = oldChar == 'a' ? 'b' : 'a';
        String invalidId = id.toString().replace(oldChar, newChar);
        given()
                .port(port)
                .auth()
                .oauth2(token)
                .when()
                .baseUri(host + port + requestMapping + "/")
                .basePath("{id}")
                .pathParam("id", invalidId)
                .contentType(ContentType.JSON)
                .delete()
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(404)
                .extract().response();
    }

    @Test
    void delete_position_when_empty_id_test() {
        Position position = new Position();
        position.setPosition("position");
        position.setDescription("position");
        positionService.addEntity(position);
        String invalidId = "";
        given()
                .port(port)
                .auth()
                .oauth2(token)
                .when()
                .baseUri(host + port + requestMapping + "/")
                .basePath("{id}")
                .pathParam("id", invalidId)
                .contentType(ContentType.JSON)
                .delete()
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(404)
                .extract().response();
    }

    @Test
    void delete_position_when_invalid_format_id_test() {
        Position position = new Position();
        position.setPosition("position");
        position.setDescription("position");
        positionService.addEntity(position);
        String invalidId = "Bad uuid";
        given()
                .port(port)
                .auth()
                .oauth2(token)
                .when()
                .baseUri(host + port + requestMapping + "/")
                .basePath("{id}")
                .pathParam("id", invalidId)
                .contentType(ContentType.JSON)
                .delete()
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(400)
                .extract().response();
    }

    @Test
    void update_position_when_valid_dto_Test() {
        Position position = new Position();
        position.setPosition("position");
        position.setDescription("position");
        position = positionService.addEntity(position);
        PositionDto positionDto = new PositionDto();
        positionDto.setDescription(position.getDescription());
        positionDto.setId(position.getId());
        positionDto.setPosition("condition");
        Response response = given()
                .port(port)
                .auth()
                .oauth2(token)
                .body(positionDto)
                .when()
                .contentType(ContentType.JSON)
                .put(host + port + requestMapping + "/update")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .extract().response();
        PositionDto actualPosition = response.as(PositionDto.class);
        Assertions.assertEquals(positionDto.getPosition(), actualPosition.getPosition());
    }

    @Test
    void update_position_when_invalid_dto_test() {
        Position position = new Position();
        position.setPosition("position");
        position.setDescription("position");
        positionService.addEntity(position);
        PositionDto positionDto = new PositionDto();
        positionDto.setId(position.getId());

        given()
                .port(port)
                .auth()
                .oauth2(token)
                .body(positionDto)
                .when()
                .contentType(ContentType.JSON)
                .put(host + port + requestMapping + "/update")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(400)
                .extract().response();
    }


}
