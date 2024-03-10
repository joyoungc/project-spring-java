package io.joyoungc.infrastructure.persistence.repository;

import io.joyoungc.domain.member.Grade;
import io.joyoungc.domain.order.OrderStatus;
import io.joyoungc.infrastructure.persistence.BaseJpaRepositoryTest;
import io.joyoungc.infrastructure.persistence.entity.MemberEntity;
import io.joyoungc.infrastructure.persistence.entity.OrderEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

class OrderJpaRepositoryTest extends BaseJpaRepositoryTest {

    @Autowired
    OrderJpaRepository orderJpaRepository;

    @Autowired
    MemberJpaRepository memberJpaRepository;
    @Test
    void test_save() {
        MemberEntity member = new MemberEntity("testName", Grade.BASIC);
        MemberEntity save = memberJpaRepository.save(member);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCreatedBy("createBy");
        orderEntity.setStatus(OrderStatus.ORDER);
        orderEntity.setMember(save);
        orderJpaRepository.save(orderEntity);
    }

    @Test
    void test_find() {
        Optional<OrderEntity> optionalOrder = orderJpaRepository.findById(1L);
        Assertions.assertThat(optionalOrder).isNotEmpty();
    }

}