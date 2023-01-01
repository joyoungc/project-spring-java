package io.joyoungc.data.shop.repository;

import io.joyoungc.data.configuration.DataConfig;
import io.joyoungc.data.shop.domain.Grade;
import io.joyoungc.data.shop.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(DataConfig.class)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void findById() {
        // given
        Member save = memberRepository.save(new Member("MyName", Grade.VIP));

        // when
        Optional<Member> member = memberRepository.findById(save.getId());

        // then
        assertThat(member).isNotEmpty();
        assertThat(member.get().getName()).isEqualTo("MyName");
    }

    @Test
    void findMembers() {
        // given
        memberRepository.save(new Member("MyName", Grade.VIP));

        // when
        List<Member> members = memberRepository.findMembers(Grade.VIP);

        // then
        assertThat(members).isNotEmpty();
    }

    @Test
    void update() {
        // given
        Member save = memberRepository.save(new Member("MyName", Grade.VIP));
        save.setGrade(Grade.BASIC);
        entityManager.flush();
        entityManager.clear();

        // when
        Member member = memberRepository.findById(save.getId()).get();

        // then
        assertThat(member.getGrade()).isEqualTo(Grade.BASIC);
    }

    @Test
    void delete() {
        // given
        Member save = memberRepository.save(new Member("MyName", Grade.VIP));
        Long id = save.getId();

        // when
        memberRepository.deleteById(id);
        entityManager.flush();
        entityManager.clear();

        // then
        Optional<Member> optionalMember = memberRepository.findById(id);
        assertThat(optionalMember).isEmpty();
    }

}