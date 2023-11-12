package io.joyoungc.api.application.service;

import io.joyoungc.api.TestJpaConfig;
import io.joyoungc.api.request.CreateMemberRequest;
import io.joyoungc.api.request.SearchMemberRequest;
import io.joyoungc.api.response.MemberResponse;
import io.joyoungc.api.service.MemberService;
import io.joyoungc.domain.member.Grade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RequiredArgsConstructor
@TestJpaConfig
class MemberServiceTest {

    private final MemberService memberService;

    @Test
    void create_member() {
        CreateMemberRequest requestUser = new CreateMemberRequest("생성", "test");
        requestUser.setGrade(Grade.VIP);
        Long id = memberService.createMember(requestUser);
        assertThat(id).isNotNull();
    }

    @Test
    void get_member() {
        CreateMemberRequest requestUser = new CreateMemberRequest("생성", "test");
        requestUser.setGrade(Grade.VIP);
        Long id = memberService.createMember(requestUser);

        MemberResponse member = memberService.getMember(id);
        assertThat(member).isNotNull();
        assertThat(member.getId()).isEqualTo(id);
    }

    @Test
    void get_members() {
        CreateMemberRequest requestUser = new CreateMemberRequest("생성", "test");
        requestUser.setGrade(Grade.VIP);
        Long id = memberService.createMember(requestUser);

        SearchMemberRequest search = new SearchMemberRequest();
        search.setGrade(Grade.VIP);
        List<MemberResponse> members = memberService.getMembers(search);
        assertThat(members).isNotEmpty()
                .extracting(MemberResponse::getId).contains(id);
    }

}