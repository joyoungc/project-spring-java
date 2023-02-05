package io.joyoungc.data.testcontainer;

import io.joyoungc.data.shop.domain.Grade;
import io.joyoungc.data.shop.domain.Member;
import io.joyoungc.data.shop.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
@SpringBootTest
@ActiveProfiles("test")
class TestContainerTest {

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.save(new Member("MyName", Grade.VIP));
    }

    @Test
    void get_member() {
        Optional<Member> optionalMember = memberRepository.findById(1L);
        assertThat(optionalMember).isNotEmpty();
    }

}
