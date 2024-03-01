package io.joyoungc.infrastructure.persistence.repository;

import io.joyoungc.domain.member.Grade;
import io.joyoungc.infrastructure.persistence.BaseJpaRepositoryTest;
import io.joyoungc.infrastructure.persistence.entity.MemberEntity;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemberJpaRepositoryTest extends BaseJpaRepositoryTest {
    @Autowired
    MemberJpaRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    Environment environment;

    @Test
    void check_properties() {
        String property1 = environment.getProperty("logging.level.jdbc.sqlonly");
        System.out.println("property1 = " + property1);
    }

    @Test
    void test_findById() {
        // given
        MemberEntity save = createMember();

        // then
        assertThat(memberRepository.findById(save.getId()))
                .isNotEmpty()
                .hasValueSatisfying(s -> {
                    assertThat(s.getName()).isEqualTo("MyName");
                    assertThat(s.getGrade()).isEqualTo(Grade.VIP);
                });
    }

    @Test
    void test_findMembers() {
        // given
        createMember();

        // when
        List<MemberEntity> members = memberRepository.findMembers(Grade.VIP);

        // then
        assertThat(members).isNotEmpty();
    }

    @Test
    void test_update() {
        // given
        MemberEntity save = createMember();
        MemberEntity member = memberRepository.findById(save.getId()).orElseThrow();

        // when
        member.setGrade(Grade.BASIC);
        entityManager.flush();
        entityManager.clear();

        // then
        assertThat(memberRepository.findById(save.getId()))
                .isNotEmpty()
                .hasValueSatisfying(s -> {
                    assertThat(s.getName()).isEqualTo("MyName");
                    assertThat(s.getGrade()).isEqualTo(Grade.BASIC);
                });
    }

    @Test
    void test_delete() {
        // given
        MemberEntity save = createMember();
        Long id = save.getId();

        // when
        memberRepository.deleteById(id);
        entityManager.flush();
        entityManager.clear();

        // then
        assertThat(memberRepository.findById(id)).isEmpty();
    }

    private MemberEntity createMember() {
        MemberEntity save = memberRepository.save(new MemberEntity("MyName", Grade.VIP));
        entityManager.clear(); // Clear persistence context
        return save;
    }

}