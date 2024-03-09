package io.joyoungc.api.member;

import io.joyoungc.api.member.request.CreateMemberRequest;
import io.joyoungc.api.member.request.SearchMemberRequest;
import io.joyoungc.api.member.response.MemberResponse;
import io.joyoungc.domain.member.Grade;
import io.joyoungc.domain.member.Member;
import io.joyoungc.domain.member.MemberRepositoryPort;
import io.joyoungc.infrastructure.constant.Profiles;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringJUnitConfig(classes = {MemberService.class})
@ActiveProfiles(Profiles.TEST)
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @MockBean
    MemberRepositoryPort memberRepositoryPort;

    @Test
    void create_member() {
        // given
        Mockito.when(memberRepositoryPort.save(any())).thenReturn(1L);

        // when
        CreateMemberRequest requestUser = new CreateMemberRequest("생성", "test");
        requestUser.setGrade(Grade.VIP);
        Long id = memberService.createMember(requestUser);

        // then
        assertThat(id).isNotNull();
    }

    @Test
    void get_member() {
        // given
        Member member = new Member();
        member.setId(1L);
        Mockito.when(memberRepositoryPort.findById(1L)).thenReturn(member);

        // when
        MemberResponse memberResponse = memberService.getMember(1L);

        // then
        assertThat(memberResponse).isNotNull();
        assertThat(memberResponse.getId()).isEqualTo(1L);
    }

    @Test
    void get_members() {
        // given
        Member member = new Member();
        member.setId(1L);
        Mockito.when(memberRepositoryPort.findMembers(Grade.VIP)).thenReturn(List.of(member));

        // when
        SearchMemberRequest search = new SearchMemberRequest();
        search.setGrade(Grade.VIP);
        List<MemberResponse> members = memberService.getMembers(search);

        // then
        assertThat(members).isNotEmpty()
                .extracting(MemberResponse::getId).contains(1L);
    }

}