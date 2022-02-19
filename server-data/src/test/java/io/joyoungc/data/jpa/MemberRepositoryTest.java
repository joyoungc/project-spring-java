package io.joyoungc.data.jpa;

import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.joyoungc.data.configuration.DataConfig;
import io.joyoungc.data.domain.shop.Member;
import io.joyoungc.data.domain.shop.MemberQueryDslRepository;
import io.joyoungc.data.domain.shop.MemberRepository;
import io.joyoungc.data.domain.shop.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static io.joyoungc.data.domain.shop.QMember.member;
import static io.joyoungc.data.domain.shop.QOrder.order;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Repository.class}), showSql = false)
@Import(DataConfig.class)
@ImportAutoConfiguration(DataSourceDecoratorAutoConfiguration.class)
class MemberRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private MemberQueryDslRepository memberQueryDslRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("영속성 예외 케이스 테스트(1차 캐시)")
    void testPersistence() {
        String testName = "테스트1";
        Member member1 = new Member(testName);

        entityManager.persist(member1);

        Order order1 = new Order();
        order1.setOrderDate(LocalDateTime.now());
        order1.setMember(member1);

        Order order2 = new Order();
        order2.setOrderDate(LocalDateTime.now().minusDays(1));
        order2.setMember(member1);

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
        Member member = new Member(testName);
        memberRepository.save(member);

        List<Member> members = memberQueryDslRepository.findMembers();
        assertThat(members).isNotEmpty();
    }

}