package io.joyoungc.api.member;

import io.joyoungc.api.BaseServerApiIntegrationTest;
import io.joyoungc.api.member.request.CreateMemberRequest;
import io.joyoungc.api.member.request.SearchMemberRequest;
import io.joyoungc.api.member.response.MemberResponse;
import io.joyoungc.domain.member.Grade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemberServiceTest extends BaseServerApiIntegrationTest {

    @Autowired
    MemberService memberService;

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