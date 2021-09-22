package io.joyoungc.api.controller;

import io.joyoungc.api.dto.UserDto;
import io.joyoungc.data.domain.user.User;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
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

    public String createUrlWithPort(String uri, int port) {
        return String.format("http://localhost:%s%s", port, uri);
    }

}