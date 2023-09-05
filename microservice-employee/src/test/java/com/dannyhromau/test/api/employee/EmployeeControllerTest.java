package com.dannyhromau.test.api.employee;

import com.dannyhromau.employee.Application;
import com.dannyhromau.employee.api.dto.EmployeeDto;
import com.dannyhromau.employee.api.dto.PositionDto;
import com.dannyhromau.employee.api.dto.UserDto;
import com.dannyhromau.employee.controller.AuthController;
import com.dannyhromau.employee.facade.impl.AuthFacadeImpl;
import com.dannyhromau.employee.model.Employee;
import com.dannyhromau.employee.model.Position;
import com.dannyhromau.employee.repository.EmployeeRepository;
import com.dannyhromau.employee.repository.PositionRepository;
import com.dannyhromau.employee.repository.UserRepository;
import com.dannyhromau.employee.service.impl.EmployeeServiceImpl;
import com.dannyhromau.employee.service.impl.PositionServiceImpl;
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

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.yml")
public class EmployeeControllerTest {

    @LocalServerPort
    private int port;
    private static String token;
    @Autowired
    private AuthController authController;
    @Autowired
    private AuthFacadeImpl authFacadeImpl;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private PositionServiceImpl positionServiceImpl;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;
    private UUID positionId;
    @Value("${host}")
    private String host;
    @Value("${employeeMapping}")
    private String requestMapping;


    @BeforeEach
    public void setup() {
        UserDto userDto = new UserDto();
        userDto.setUsername("user");
        userDto.setEmail("user@user.com");
        userDto.setPassword("11111111");
        authFacadeImpl.register(userDto);
        token = Objects.requireNonNull(authController.login(userDto).getBody()).getAccessToken();
        Position position = new Position();
        position.setPosition("position");
        position.setDescription("position");
        positionId = positionServiceImpl.addEntity(position).getId();
    }

    @AfterEach
    public void delete() {
        userRepository.deleteAll();
        employeeRepository.deleteAll();
        positionRepository.deleteAll();
    }

    @Test
    void check_non_empty_employee_list_test() {
        int expectedListSize = 3;
        for (int i = 0; i < expectedListSize; i++) {
            Employee employee = new Employee();
            employee.setPositionId(positionId);
            employee.setFirstName("test" + i);
            employee.setFired(false);
            employee.setLastName("test" + i);
            employee.setBirthDate(LocalDate.now());
            employee.setPhone("12345678" + i);
            employee.setFullTime(true);
            employeeServiceImpl.addEntity(employee);
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
    void get_employee_when_valid_id_test() {
        Employee employee = new Employee();
        employee.setPositionId(positionId);
        employee.setFirstName("employee");
        employee.setFired(false);
        employee.setLastName("employee");
        employee.setBirthDate(LocalDate.now());
        employee.setPhone("12345678");
        employee.setFullTime(true);
        UUID id = employeeServiceImpl.addEntity(employee).getId();
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
        EmployeeDto actualEmployeeDto = response.as(EmployeeDto.class);
        Assertions.assertEquals(employee.getLastName(), actualEmployeeDto.getLastName());
        Assertions.assertEquals(employee.getPhone(), actualEmployeeDto.getPhone());
    }

    @Test
    void get_employee_when_id_not_exists_test() {
        Employee employee = new Employee();
        employee.setPositionId(positionId);
        employee.setFirstName("employee");
        employee.setFired(false);
        employee.setLastName("employee");
        employee.setBirthDate(LocalDate.now());
        employee.setPhone("12345678");
        employee.setFullTime(true);
        UUID id = employeeServiceImpl.addEntity(employee).getId();
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
    void get_employee_when_empty_id_test() {
        Employee employee = new Employee();
        employee.setPositionId(positionId);
        employee.setFirstName("employee");
        employee.setFired(false);
        employee.setLastName("employee");
        employee.setBirthDate(LocalDate.now());
        employee.setPhone("12345678");
        employee.setFullTime(true);
        employeeServiceImpl.addEntity(employee);
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
    void get_employee_when_invalid_format_id_test() {
        Employee employee = new Employee();
        employee.setPositionId(positionId);
        employee.setFirstName("employee");
        employee.setFired(false);
        employee.setLastName("employee");
        employee.setBirthDate(LocalDate.now());
        employee.setPhone("12345678");
        employee.setFullTime(true);
        employeeServiceImpl.addEntity(employee);
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
    void create_employee_when_valid_dto_test() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("test");
        employeeDto.setLastName("test");
        employeeDto.setBirthDate(LocalDate.now());
        employeeDto.setPhone("1234567");
        employeeDto.setFired(false);
        employeeDto.setPositionId(positionId);
        Response response = given()
                .port(port)
                .auth()
                .oauth2(token)
                .body(employeeDto)
                .when()
                .contentType(ContentType.JSON)
                .post(host + port + requestMapping + "/create")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .extract().response();
        EmployeeDto actualEmployee = response.as(EmployeeDto.class);
        Assertions.assertEquals(employeeDto.getLastName(), actualEmployee.getLastName());
        Assertions.assertEquals(employeeDto.getPhone(), actualEmployee.getPhone());
    }

    @Test
    void create_employee_when_existing_dto_test() {
        Employee employee = new Employee();
        employee.setPositionId(positionId);
        employee.setFirstName("employee");
        employee.setFired(false);
        employee.setLastName("employee");
        employee.setBirthDate(LocalDate.now());
        employee.setPhone("12345678");
        employee.setFullTime(true);
        employeeServiceImpl.addEntity(employee);
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("employee");
        employeeDto.setLastName("employee");
        employeeDto.setBirthDate(LocalDate.now());
        employeeDto.setPhone("12345678");
        employeeDto.setFired(false);
        employeeDto.setPositionId(positionId);
        given()
                .port(port)
                .auth()
                .oauth2(token)
                .body(employeeDto)
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
    void create_employee_when_invalid_dto_test() {
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
    void delete_employee_when_valid_id_test() {
        Employee employee = new Employee();
        employee.setPositionId(positionId);
        employee.setFirstName("employee");
        employee.setFired(false);
        employee.setLastName("employee");
        employee.setBirthDate(LocalDate.now());
        employee.setPhone("12345678");
        employee.setFullTime(true);
        UUID id = employeeServiceImpl.addEntity(employee).getId();
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
    void delete_employee_when_id_not_exists_test() {
        Employee employee = new Employee();
        employee.setPositionId(positionId);
        employee.setFirstName("employee");
        employee.setFired(false);
        employee.setLastName("employee");
        employee.setBirthDate(LocalDate.now());
        employee.setPhone("12345678");
        employee.setFullTime(true);
        UUID id = employeeServiceImpl.addEntity(employee).getId();
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
    void delete_employee_when_empty_id_test() {
        Employee employee = new Employee();
        employee.setPositionId(positionId);
        employee.setFirstName("employee");
        employee.setFired(false);
        employee.setLastName("employee");
        employee.setBirthDate(LocalDate.now());
        employee.setPhone("12345678");
        employee.setFullTime(true);
        employeeServiceImpl.addEntity(employee);
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
    void delete_employee_when_invalid_format_id_test() {
        Employee employee = new Employee();
        employee.setPositionId(positionId);
        employee.setFirstName("employee");
        employee.setFired(false);
        employee.setLastName("employee");
        employee.setBirthDate(LocalDate.now());
        employee.setPhone("12345678");
        employee.setFullTime(true);
        employeeServiceImpl.addEntity(employee);
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
    void update_employee_when_valid_dto_Test() {
        Employee employee = new Employee();
        employee.setPositionId(positionId);
        employee.setFirstName("employee");
        employee.setFired(false);
        employee.setLastName("employee");
        employee.setBirthDate(LocalDate.now());
        employee.setPhone("12345678");
        employee.setFullTime(true);
        employeeServiceImpl.addEntity(employee);
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName("employee");
        employeeDto.setLastName("test");
        employeeDto.setBirthDate(LocalDate.now());
        employeeDto.setPhone("12345679");
        employeeDto.setFired(false);
        employeeDto.setPositionId(positionId);
        Response response = given()
                .port(port)
                .auth()
                .oauth2(token)
                .body(employeeDto)
                .when()
                .contentType(ContentType.JSON)
                .put(host + port + requestMapping + "/update")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .extract().response();
        EmployeeDto actualDto = response.as(EmployeeDto.class);
        Assertions.assertEquals(employeeDto.getLastName(), actualDto.getLastName());
        Assertions.assertEquals(employeeDto.getPhone(), actualDto.getPhone());
    }

    @Test
    void update_position_when_invalid_dto_test() {
        Employee employee = new Employee();
        employee.setPositionId(positionId);
        employee.setFirstName("employee");
        employee.setFired(false);
        employee.setLastName("employee");
        employee.setBirthDate(LocalDate.now());
        employee.setPhone("12345678");
        employee.setFullTime(true);
        employeeServiceImpl.addEntity(employee);
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());

        given()
                .port(port)
                .auth()
                .oauth2(token)
                .body(employeeDto)
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
