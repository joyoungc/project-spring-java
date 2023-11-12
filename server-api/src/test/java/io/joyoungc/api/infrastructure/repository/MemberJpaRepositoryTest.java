package io.joyoungc.api.infrastructure.repository;

import io.joyoungc.api.infrastructure.BaseJpaRepositoryTest;
import io.joyoungc.data.jpa.domain.MemberEntity;
import io.joyoungc.domain.member.Grade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemberJpaRepositoryTest extends BaseJpaRepositoryTest {
    @Autowired
    MemberJpaRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void find_by_id() {
        // given
        MemberEntity save = memberRepository.save(new MemberEntity("MyName", Grade.VIP));
        entityManager.clear(); // Clear persistence context

        // when
        Optional<MemberEntity> member = memberRepository.findById(save.getId());

        // then
        assertThat(member).isNotEmpty();
        assertThat(member.get().getName()).isEqualTo("MyName");
    }

    @Test
    void find_members() {
        // given
        memberRepository.save(new MemberEntity("MyName", Grade.VIP));

        // when
        List<MemberEntity> members = memberRepository.findMembers(Grade.VIP);

        // then
        assertThat(members).isNotEmpty();
    }

    @Test
    void update() {
        // given
        MemberEntity save = memberRepository.save(new MemberEntity("MyName", Grade.VIP));
        save.setGrade(Grade.BASIC);
        entityManager.flush();
        entityManager.clear();

        // when
        MemberEntity member = memberRepository.findById(save.getId()).get();

        // then
        assertThat(member.getGrade()).isEqualTo(Grade.BASIC);
    }

    @Test
    void delete() {
        // given
        MemberEntity save = memberRepository.save(new MemberEntity("MyName", Grade.VIP));
        Long id = save.getId();

        // when
        memberRepository.deleteById(id);
        entityManager.flush();
        entityManager.clear();

        // then
        Optional<MemberEntity> optionalMember = memberRepository.findById(id);
        assertThat(optionalMember).isEmpty();
    }

}