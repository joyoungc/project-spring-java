package io.joyoungc.data.jpa;

import io.joyoungc.data.BaseDataJpaTest;
import io.joyoungc.data.shop.domain.Grade;
import io.joyoungc.data.shop.domain.Member;
import io.joyoungc.data.shop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemberRepositoryTest extends BaseDataJpaTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void test_QueryDsl_repository() {
        String testName = "테스트1";
        Member member = new Member(testName, Grade.BASIC);
        memberRepository.save(member);

        List<Member> members = memberRepository.findMembers(Grade.BASIC);
        assertThat(members).isNotEmpty();
    }

}