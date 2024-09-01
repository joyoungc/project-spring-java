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
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepositoryPort memberRepositoryPort;

    @Test
    void test_createMember() {
        // given
        CreateMemberRequest requestMember = new CreateMemberRequest("생성", "test");
        requestMember.setGrade(Grade.VIP);
        given(memberRepositoryPort.save(
                argThat(o -> o.getName().equals(requestMember.getName()) &&
                        o.getGrade().equals(requestMember.getGrade()))
        )).willReturn(1L);

        // when
        Long id = memberService.createMember(requestMember);

        // then
        assertThat(id).isNotNull().isEqualTo(1L);
    }

    @Test
    void test_getMember() {
        // given
        long memberId = 1000L;
        Member member = new Member("Jada", Grade.VIP);
        member.setId(memberId);
        given(memberRepositoryPort.findById(memberId)).willReturn(member);

        // when
        MemberResponse memberResponse = memberService.getMember(memberId);

        // then
        assertThat(memberResponse)
                .isNotNull()
                .extracting(MemberResponse::getId, MemberResponse::getGrade)
                .doesNotContainNull()
                .containsExactly(memberId, Grade.VIP);
    }

    @Test
    void test_getMembers() {
        // given
        long memberId = 1000L;
        Member member = new Member("Shamela", Grade.VIP);
        member.setId(memberId);
        given(memberRepositoryPort.findMembers(Grade.VIP)).willReturn(List.of(member));

        // when
        SearchMemberRequest search = new SearchMemberRequest(Grade.VIP);
        List<MemberResponse> members = memberService.getMembers(search);

        // then
        assertThat(members).isNotEmpty();
        assertThat(members.getFirst())
                .extracting(MemberResponse::getId, MemberResponse::getName, MemberResponse::getGrade)
                .containsExactly(memberId, member.getName(), member.getGrade());
    }

}