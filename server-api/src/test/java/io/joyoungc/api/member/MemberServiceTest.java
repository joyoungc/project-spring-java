package io.joyoungc.api.member;

import io.joyoungc.api.member.request.CreateMemberRequest;
import io.joyoungc.api.member.request.SearchMemberRequest;
import io.joyoungc.api.member.response.MemberResponse;
import io.joyoungc.domain.shop.member.Grade;
import io.joyoungc.domain.shop.member.Member;
import io.joyoungc.domain.shop.member.MemberRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepositoryPort memberRepositoryPort;

    @Test
    void test_createMember() {
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
    void test_getMember() {
        // given
        Member member = new Member();
        member.setId(1L);
        member.setGrade(Grade.VIP);
        member.setName("Jada");
        Mockito.when(memberRepositoryPort.findById(1L)).thenReturn(member);

        // when
        MemberResponse memberResponse = memberService.getMember(1L);

        // then
        assertThat(memberResponse).isNotNull();
        assertThat(memberResponse.getId()).isEqualTo(1L);
        assertThat(memberResponse.getGrade()).isEqualTo(Grade.VIP);
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