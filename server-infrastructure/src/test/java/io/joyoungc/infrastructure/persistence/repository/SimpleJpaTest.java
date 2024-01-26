package io.joyoungc.infrastructure.persistence.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.joyoungc.domain.common.constant.CommonError;
import io.joyoungc.domain.common.exception.ApplicationException;
import io.joyoungc.infrastructure.persistence.BaseJpaRepositoryTest;
import io.joyoungc.infrastructure.persistence.entity.MemberEntity;
import io.joyoungc.infrastructure.persistence.entity.OrderEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static io.joyoungc.infrastructure.persistence.entity.QMemberEntity.memberEntity;
import static io.joyoungc.infrastructure.persistence.entity.QOrderEntity.orderEntity;
import static org.assertj.core.api.Assertions.assertThat;

/***
 * Created by Aiden Jeong on 2022.04.02
 */
class SimpleJpaTest extends BaseJpaRepositoryTest {

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private MemberJpaRepository memberRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("연관관계 주인")
    void test_mapped_by() {
        // given
        MemberEntity member1 = new MemberEntity("테스트1");
        em.persist(member1);

        OrderEntity order1 = new OrderEntity(member1, null, 100L, LocalDateTime.now());
        OrderEntity order2 = new OrderEntity(member1, null, 200L, LocalDateTime.now().minusDays(1));
        OrderEntity order3 = new OrderEntity(member1, null, 300L, LocalDateTime.now().minusDays(3));

        em.persist(order1);
        em.persist(order2);
        em.persist(order3);

        em.flush();
        em.clear();

        MemberEntity member = memberRepository.findById(member1.getId()).orElse(null);
        OrderEntity order4 = new OrderEntity(member, null, 2345678L, LocalDateTime.now().minusDays(3));
        member.getOrders().add(order4);

        em.flush();

        System.out.println("member = " + member);
    }

    @Test
    @DisplayName("N + 1 호출")
    void select_all() {

        MemberEntity member1 = new MemberEntity("테스트1");
        em.persist(member1);

        OrderEntity order1 = new OrderEntity(member1, null, 100L, LocalDateTime.now());
        OrderEntity order2 = new OrderEntity(member1, null, 200L, LocalDateTime.now().minusDays(1));
        OrderEntity order3 = new OrderEntity(member1, null, 300L, LocalDateTime.now().minusDays(3));

        em.persist(order1);
        em.persist(order2);
        em.persist(order3);

        MemberEntity member2 = new MemberEntity("테스트2");
        em.persist(member2);

        OrderEntity order4 = new OrderEntity(member2, null, 400L, LocalDateTime.now());
        OrderEntity order5 = new OrderEntity(member2, null, 500L, LocalDateTime.now().minusDays(1));

        em.persist(order4);
        em.persist(order5);

        MemberEntity member3 = new MemberEntity("테스트3");
        em.persist(member3);

        em.flush();
        em.clear();

        List<MemberEntity> all = memberRepository.findAll();
        Assertions.assertThat(all).isNotNull();
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
                        memberEntity.name,
                        orderEntity.id,
                        orderEntity.orderDate,
                        orderEntity.status,
                        orderEntity.discountPrice
                )
                .from(memberEntity)
                .leftJoin(memberEntity.orders, orderEntity)
                .fetch();

        for (Tuple tuple : tuples) {
            System.out.println("tuple = " + tuple);
        }

        Assertions.assertThat(tuples).isNotEmpty();
    }



    @Test
    @DisplayName("영속성 예외 케이스 테스트(1차 캐시)")
    void test_persistence() {
        makeTestData();
        MemberEntity result = queryFactory
                .selectFrom(memberEntity)
                .join(memberEntity.orders, orderEntity).fetchJoin()
                .where(memberEntity.name.eq("테스트1")).fetchOne();

        List<OrderEntity> orders = result.getOrders();
        Assertions.assertThat(orders).isEmpty();
    }

    @Test
    @DisplayName("N + 1 테스트")
    @Rollback(value = false)
    void getOrders() {
        MemberEntity member1 = new MemberEntity("테스트1");
        em.persist(member1);

        OrderEntity order1 = new OrderEntity(member1, null, 100L, LocalDateTime.now());
        OrderEntity order2 = new OrderEntity(member1, null, 200L, LocalDateTime.now().minusDays(1));
        OrderEntity order3 = new OrderEntity(member1, null, 300L, LocalDateTime.now().minusDays(3));

        em.persist(order1);
        em.persist(order2);
        em.persist(order3);

        MemberEntity member2 = new MemberEntity("테스트2");
        em.persist(member2);

        OrderEntity order4 = new OrderEntity(member2, null, 400L, LocalDateTime.now());
        OrderEntity order5 = new OrderEntity(member2, null, 500L, LocalDateTime.now().minusDays(1));

        em.persist(order4);
        em.persist(order5);

        MemberEntity member3 = new MemberEntity("테스트3");
        em.persist(member3);

        em.flush();
        em.clear();
        // findById() 같은 경우는 영속성 컨텍스트를 먼저 찾고 영속성 컨텍스트에 해당 엔티티가 있으면 그 값을 바로 리턴. (1차 캐시)
        MemberEntity member = memberRepository.findById(member1.getId())
                .orElseThrow(() -> new ApplicationException(CommonError.COMMON_NOT_FOUND));

        assertThat(member).isNotNull();
        assertThat(member.getOrders().size()).isEqualTo(3);
    }

    private void makeTestData() {

        MemberEntity member1 = new MemberEntity("테스트1");
        em.persist(member1);

        OrderEntity order1 = new OrderEntity(member1, null, 100L, LocalDateTime.now());
        OrderEntity order2 = new OrderEntity(member1, null, 200L, LocalDateTime.now().minusDays(1));
        OrderEntity order3 = new OrderEntity(member1, null, 300L, LocalDateTime.now().minusDays(3));

        em.persist(order1);
        em.persist(order2);
        em.persist(order3);

        MemberEntity member2 = new MemberEntity("테스트2");
        em.persist(member2);

        OrderEntity order4 = new OrderEntity(member2, null, 400L, LocalDateTime.now());
        OrderEntity order5 = new OrderEntity(member2, null, 500L, LocalDateTime.now().minusDays(1));

        em.persist(order4);
        em.persist(order5);

        MemberEntity member3 = new MemberEntity("테스트3");
        em.persist(member3);

    }
}
