package io.joyoungc.api.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.joyoungc.api.BaseServerApiIntegrationTest;
import io.joyoungc.api.member.mapper.MemberMapper;
import io.joyoungc.api.member.request.CreateMemberRequest;
import io.joyoungc.api.member.request.SearchMemberRequest;
import io.joyoungc.api.member.response.MemberResponse;
import io.joyoungc.application.input.MemberCommand;
import io.joyoungc.application.input.MemberService;
import io.joyoungc.domain.enums.ResponseCode;
import io.joyoungc.domain.model.common.CommonResponse;
import io.joyoungc.domain.model.member.Grade;
import io.joyoungc.domain.model.member.Member;
import io.joyoungc.infrastructure.constant.Profiles;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import static io.joyoungc.api.TestUtils.createUrlWithPort;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureWebMvc
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class,
        MongoRepositoriesAutoConfiguration.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles(Profiles.TEST)
class MemberControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    MemberService memberService;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("회원 등록 API 테스트")
    void test_createMember() throws Exception {
        // given
        CreateMemberRequest request = new CreateMemberRequest("이름", "createId");

        // when
        String response = mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        CommonResponse commonResponse = objectMapper.readValue(response, CommonResponse.class);
        assertThat(commonResponse.getCode()).isEqualTo(ResponseCode.SUCCESS.getCode());
        assertThat(commonResponse.getMessage()).contains("memberId");
    }

    @Test
    @Order(2)
    @DisplayName("회원 전체 조회 API 테스트")
    void test_getMembers() throws Exception {
        // when
        String response = mockMvc.perform(get("/api/members")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        MemberResponse[] members = objectMapper.readValue(response, MemberResponse[].class);
        assertThat(members).isNotEmpty();
        assertThat(members[0].getName()).isEqualTo("이름");
        assertThat(members[0].getGrade()).isEqualTo(Grade.BASIC);
        assertThat(members[0].getCreatedAt()).isNotNull();
        assertThat(members[0].getModifiedAt()).isNull();
    }

    @Test
    @Order(3)
    @DisplayName("회원 단건 조회 API 테스트")
    void test_getMember() throws Exception {
        // when
        String response = mockMvc.perform(get("/api/members/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        MemberResponse member = objectMapper.readValue(response, MemberResponse.class);
        assertThat(member).isNotNull();
        assertThat(member.getName()).isEqualTo("이름");
        assertThat(member.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("회원 등록 시 유효성 검증 테스트 - 이름 누락")
    void test_createMember_validation_name_blank() throws Exception {
        // given
        CreateMemberRequest request = new CreateMemberRequest("", "createId");

        // when & then
        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 회원 조회 테스트")
    void test_getMember_not_found() throws Exception {
        // when & then
        mockMvc.perform(get("/api/members/999999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    @Disabled
    void get_cached_members() {
        // 테스트 사용자 등록
        CreateMemberRequest requestUser = new CreateMemberRequest("테스트", "생성자");
        Member member = MemberMapper.INSTANCE.toMember(requestUser);
        Long userId = memberService.createMember(member);

        SearchMemberRequest search = new SearchMemberRequest(Grade.BASIC);

        MemberCommand command = MemberMapper.INSTANCE.toSearch(search);
        // redis json
        List<Member> users = memberService.getMembers(command);
        assertThat(users).isNotEmpty();

        // 한번 더 조회시 캐시 여부 확인
        List<Member> users1 = memberService.getMembers(command);
        assertThat(users1).isNotEmpty();
    }

}