package io.joyoungc.api.member.service;

import io.joyoungc.api.TestJpaConfig;
import io.joyoungc.api.member.dto.MemberDto;
import io.joyoungc.data.shop.domain.Grade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RequiredArgsConstructor
@TestJpaConfig
class MemberServiceTest {

    private final MemberService memberService;

    static Long memberId;

    @Test
    @Order(0)
    void createMember() {
        MemberDto.RequestUser requestUser = new MemberDto.RequestUser("생성", "test");
        requestUser.setGrade(Grade.VIP);
        memberId = memberService.createMember(requestUser);
    }

    @Test
    @Order(1)
    void getMember() {
        MemberDto.ResponseUser member = memberService.getMember(memberId);
        assertThat(member).isNotNull();
        assertThat(member.getId()).isEqualTo(memberId);
    }

    @Test
    @Order(1)
    void getMembers() {
        MemberDto.Search search = new MemberDto.Search();
        search.setGrade(Grade.VIP);
        List<MemberDto.ResponseUser> members = memberService.getMembers(search);
        assertThat(members).isNotEmpty()
                .extracting(MemberDto.ResponseUser::getId).contains(memberId);
    }

}