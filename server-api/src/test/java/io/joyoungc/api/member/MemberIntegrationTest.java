package io.joyoungc.api.member;

import io.joyoungc.api.TestJpaConfig;
import io.joyoungc.api.member.dto.MemberDto;
import io.joyoungc.api.member.service.MemberService;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
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
@TestJpaConfig
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberService memberService;

    private static final String API_ENDPOINT = "/api/members";

    @Test
    @DisplayName("회원 등록")
    @Order(1)
    void createUser() {
        String url = createUrlWithPort(API_ENDPOINT, port);
        given().log().all()
                .contentType(ContentType.JSON)
                .body(
                        new MemberDto.RequestUser("이름", "createId")
                )
                .when().post(url)
                .then().log().body()
                .statusCode(200);
    }

    @Test
    @DisplayName("회원 목록 조회")
    @Order(2)
    void getMembers() {
        String url = createUrlWithPort(API_ENDPOINT, port);

        // given
        Response response = given().log().all()
                // when
                .when().get(url)
                // then
                .then()
                .log().body()
                .statusCode(200)
                .extract().response();

        List<MemberDto.ResponseUser> list = response.jsonPath().getList(".", MemberDto.ResponseUser.class);

        assertThat(list).isNotEmpty().element(0).satisfies(
                c -> {
                    assertThat(c.getName()).isEqualTo("이름");
                    assertThat(c.getModifiedDate()).isNull();
                    assertThat(c.getCreatedDate()).isNotNull();
                }
        );
    }

    @Test
    @DisplayName("회원 조회")
    @Order(3)
    void getMember() {
        String path = API_ENDPOINT + "/1";
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

        MemberDto.ResponseUser object = response.jsonPath().getObject(".", MemberDto.ResponseUser.class);

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
    @DisplayName("회원 목록 캐시 조회")
    @Disabled
    void getCacheUser() {
        // 테스트 사용자 등록
        MemberDto.RequestUser requestUser = new MemberDto.RequestUser("테스트", "생성자");
        Long userId = memberService.createMember(requestUser);

        MemberDto.Search search = new MemberDto.Search();
        // redis json
        List<MemberDto.ResponseUser> users = memberService.getMembers(search);
        assertThat(users).isNotEmpty();

        // 한번 더 조회시 캐시 여부 확인
        List<MemberDto.ResponseUser> users1 = memberService.getMembers(search);
        assertThat(users1).isNotEmpty();
    }

    public String createUrlWithPort(String uri, int port) {
        return String.format("http://localhost:%s%s", port, uri);
    }

}