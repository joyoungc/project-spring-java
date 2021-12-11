package io.joyoungc.api.controller;

import io.joyoungc.api.dto.UserDto;
import io.joyoungc.api.service.UserService;
import io.joyoungc.data.domain.user.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("회원 등록")
    @Order(1)
    void createUser() {
        String url = createUrlWithPort("/api/users", port);
        given().log().all()
                .contentType(ContentType.JSON)
                .body(
                        new UserDto.RequestUser("이름", "createId")
                )
                .when().post(url)
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("회원 조회")
    @Order(2)
    void getUser() {
        String url = createUrlWithPort("/api/users", port);

        // given
        List<User> list = given().log().all()
                // when
                .when().get(url)
                // then
                .then()
                .log().body()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", User.class);

        assertThat(list).isNotEmpty().element(0).satisfies(
                c -> {
                    assertThat(c.getName()).isEqualTo("이름");
                    assertThat(c.getModifiedDate()).isNull();
                    assertThat(c.getCreatedDate()).isNotNull();
                }
        );
    }

    @Test
    @DisplayName("회원 조회(캐시)")
    @Order(3)
    void getUserCache() {
        String path = "/api/users/1";
        String url = createUrlWithPort(path, port);

        Response response =
                // given
                given().log().all()
                        // when
                        .when().get(url)
                        // then
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().response();

        UserDto.ResponseUser object = response.jsonPath().getObject(".", UserDto.ResponseUser.class);

        assertThat(object).isNotNull().satisfies(
                c -> {
                    assertThat(c.getName()).isEqualTo("이름");
                    assertThat(c.getId()).isEqualTo(1);
                });

        // given
        given().log().all()
                // when
                .when().get(url)
                // then
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    void getCacheUser() {
        // 테스트 사용자 등록
        UserDto.RequestUser requestUser = new UserDto.RequestUser("테스트", "생성자");
        Long userId = userService.createUser(requestUser);

        // redis json
        List<UserDto.ResponseUser> users = userService.getUsers();
        assertThat(users).isNotEmpty();

        List<UserDto.ResponseUser> users1 = userService.getUsers();
        assertThat(users1).isNotEmpty();
    }

    public String createUrlWithPort(String uri, int port) {
        return String.format("http://localhost:%s%s", port, uri);
    }

}