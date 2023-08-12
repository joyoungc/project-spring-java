package io.joyoungc.data.jpa;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.joyoungc.common.CommonError;
import io.joyoungc.common.exception.ApplicationException;
import io.joyoungc.data.BaseDataJpaTest;
import io.joyoungc.data.shop.domain.Member;
import io.joyoungc.data.shop.domain.Order;
import io.joyoungc.data.shop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static io.joyoungc.data.shop.domain.QMember.member;
import static io.joyoungc.data.shop.domain.QOrder.order;
import static org.assertj.core.api.Assertions.assertThat;

/***
 * Created by Aiden Jeong on 2022.04.02
 */
class SimpleJpaTest extends BaseDataJpaTest {

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("연관관계 주인")
    void test_mapped_by() {
        // given
        Member member1 = new Member("테스트1");
        em.persist(member1);

        Order order1 = new Order(member1, null, 100L, LocalDateTime.now());
        Order order2 = new Order(member1, null, 200L, LocalDateTime.now().minusDays(1));
        Order order3 = new Order(member1, null, 300L, LocalDateTime.now().minusDays(3));

        em.persist(order1);
        em.persist(order2);
        em.persist(order3);

        em.flush();
        em.clear();

        Member member = memberRepository.findById(member1.getId()).orElse(null);
        Order order4 = new Order(member, null, 2345678L, LocalDateTime.now().minusDays(3));
        member.getOrders().add(order4);

        em.flush();

        System.out.println("member = " + member);
    }

    @Test
    @DisplayName("N + 1 호출")
    void select_all() {

        Member member1 = new Member("테스트1");
        em.persist(member1);

        Order order1 = new Order(member1, null, 100L, LocalDateTime.now());
        Order order2 = new Order(member1, null, 200L, LocalDateTime.now().minusDays(1));
        Order order3 = new Order(member1, null, 300L, LocalDateTime.now().minusDays(3));

        em.persist(order1);
        em.persist(order2);
        em.persist(order3);

        Member member2 = new Member("테스트2");
        em.persist(member2);

        Order order4 = new Order(member2, null, 400L, LocalDateTime.now());
        Order order5 = new Order(member2, null, 500L, LocalDateTime.now().minusDays(1));

        em.persist(order4);
        em.persist(order5);

        Member member3 = new Member("테스트3");
        em.persist(member3);

        em.flush();
        em.clear();

        List<Member> all = memberRepository.findAll();
        assertThat(all).isNotNull();
        assertThat(all.get(0).getOrders()).isNotEmpty();
        assertThat(all.get(1).getOrders()).isNotEmpty();
        assertThat(all.get(2).getOrders()).isEmpty();
    }

    @Test
    @DisplayName("QueryDsl 로 N + 1 조회 회피")
    void use_QueryDsl() {
        makeTestData();

        List<Tuple> tuples = queryFactory
                .select(
                        member.name,
                        order.id,
                        order.orderDate,
                        order.status,
                        order.discountPrice
                )
                .from(member)
                .leftJoin(member.orders, order)
                .fetch();

        for (Tuple tuple : tuples) {
            System.out.println("tuple = " + tuple);
        }

        assertThat(tuples).isNotEmpty();
    }



    @Test
    @DisplayName("영속성 예외 케이스 테스트(1차 캐시)")
    void test_persistence() {
        makeTestData();
        Member result = queryFactory
                .selectFrom(member)
                .join(member.orders, order).fetchJoin()
                .where(member.name.eq("테스트1")).fetchOne();

        List<Order> orders = result.getOrders();
        assertThat(orders).isEmpty();
    }

    @Test
    @DisplayName("N + 1 테스트")
    @Rollback(value = false)
    void getOrders() {
        Member member1 = new Member("테스트1");
        em.persist(member1);

        Order order1 = new Order(member1, null, 100L, LocalDateTime.now());
        Order order2 = new Order(member1, null, 200L, LocalDateTime.now().minusDays(1));
        Order order3 = new Order(member1, null, 300L, LocalDateTime.now().minusDays(3));

        em.persist(order1);
        em.persist(order2);
        em.persist(order3);

        Member member2 = new Member("테스트2");
        em.persist(member2);

        Order order4 = new Order(member2, null, 400L, LocalDateTime.now());
        Order order5 = new Order(member2, null, 500L, LocalDateTime.now().minusDays(1));

        em.persist(order4);
        em.persist(order5);

        Member member3 = new Member("테스트3");
        em.persist(member3);

        em.flush();
        em.clear();
        // findById() 같은 경우는 영속성 컨텍스트를 먼저 찾고 영속성 컨텍스트에 해당 엔티티가 있으면 그 값을 바로 리턴. (1차 캐시)
        Member member = memberRepository.findById(member1.getId())
                .orElseThrow(() -> new ApplicationException(CommonError.COMMON_NOT_FOUND));

        assertThat(member).isNotNull();
        assertThat(member.getOrders().size()).isEqualTo(3);
    }

    private void makeTestData() {

        Member member1 = new Member("테스트1");
        em.persist(member1);

        Order order1 = new Order(member1, null, 100L, LocalDateTime.now());
        Order order2 = new Order(member1, null, 200L, LocalDateTime.now().minusDays(1));
        Order order3 = new Order(member1, null, 300L, LocalDateTime.now().minusDays(3));

        em.persist(order1);
        em.persist(order2);
        em.persist(order3);

        Member member2 = new Member("테스트2");
        em.persist(member2);

        Order order4 = new Order(member2, null, 400L, LocalDateTime.now());
        Order order5 = new Order(member2, null, 500L, LocalDateTime.now().minusDays(1));

        em.persist(order4);
        em.persist(order5);

        Member member3 = new Member("테스트3");
        em.persist(member3);

    }
}
