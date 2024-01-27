package io.joyoungc.api.member;

import io.joyoungc.api.member.request.CreateMemberRequest;
import io.joyoungc.api.member.request.SearchMemberRequest;
import io.joyoungc.api.member.response.MemberResponse;
import io.joyoungc.infrastructure.persistence.TestJpaConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static io.joyoungc.api.TestUtils.createUrlWithPort;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@TestJpaConfig
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberService memberService;

    private static final String API_ENDPOINT = "/api/members";

    @Test
    @Order(1)
    void create_member() {
        String url = createUrlWithPort(API_ENDPOINT, port);
        given().log().all()
                .contentType(ContentType.JSON)
                .body(
                        new CreateMemberRequest("이름", "createId")
                )
                .when().post(url)
                .then().log().body()
                .statusCode(200);
    }

    @Test
    @Order(2)
    void get_members() {
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

        List<MemberResponse> list = response.jsonPath().getList(".", MemberResponse.class);

        assertThat(list).isNotEmpty().element(0).satisfies(
                c -> {
                    assertThat(c.getName()).isEqualTo("이름");
                    assertThat(c.getModifiedDate()).isNull();
                    assertThat(c.getCreatedDate()).isNotNull();
                }
        );
    }

    @Test
    @Order(3)
    void get_member() {
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

        MemberResponse object = response.jsonPath().getObject(".", MemberResponse.class);

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
    @Disabled
    void get_cached_members() {
        // 테스트 사용자 등록
        CreateMemberRequest requestUser = new CreateMemberRequest("테스트", "생성자");
        Long userId = memberService.createMember(requestUser);

        SearchMemberRequest search = new SearchMemberRequest();
        // redis json
        List<MemberResponse> users = memberService.getMembers(search);
        assertThat(users).isNotEmpty();

        // 한번 더 조회시 캐시 여부 확인
        List<MemberResponse> users1 = memberService.getMembers(search);
        assertThat(users1).isNotEmpty();
    }

}