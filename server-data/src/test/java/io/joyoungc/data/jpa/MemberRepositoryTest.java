package io.joyoungc.data.jpa;

import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.joyoungc.data.configuration.DataConfig;
import io.joyoungc.data.shop.domain.Grade;
import io.joyoungc.data.shop.domain.Member;
import io.joyoungc.data.shop.domain.Order;
import io.joyoungc.data.shop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static io.joyoungc.data.shop.domain.QMember.member;
import static io.joyoungc.data.shop.domain.QOrder.order;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
//@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Repository.class}), showSql = false)
@DataJpaTest(showSql = false)
@Import(DataConfig.class)
@ImportAutoConfiguration(DataSourceDecoratorAutoConfiguration.class)
class MemberRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("영속성 예외 케이스 테스트(1차 캐시)")
    void testPersistence() {
        String testName = "테스트1";
        Member member1 = new Member(testName);

        entityManager.persist(member1);

        Order order1 = new Order(member1, null, 0L, LocalDateTime.now());

        Order order2 = new Order(member1, null, 0L, LocalDateTime.now().minusDays(1));

        entityManager.persist(order1);
        entityManager.persist(order2);

        Member result = queryFactory
                .selectFrom(member)
                .join(member.orders, order).fetchJoin()
                .where(member.name.eq(testName)).fetchOne();

        List<Order> orders = result.getOrders();
        assertThat(orders).isEmpty();
    }

    @Test
    @DisplayName("QueryDsl 레포지토리 테스트")
    void testQueryDsl() {
        String testName = "테스트1";
        Member member = new Member(testName, Grade.BASIC);
        memberRepository.save(member);

        List<Member> members = memberRepository.findMembers(Grade.BASIC);
        assertThat(members).isNotEmpty();
    }

}