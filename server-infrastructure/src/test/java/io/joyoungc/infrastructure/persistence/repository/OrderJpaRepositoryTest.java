package io.joyoungc.infrastructure.persistence.repository;

import io.joyoungc.domain.member.Grade;
import io.joyoungc.domain.order.OrderStatus;
import io.joyoungc.infrastructure.persistence.BaseJpaRepositoryTest;
import io.joyoungc.infrastructure.persistence.entity.MemberEntity;
import io.joyoungc.infrastructure.persistence.entity.OrderEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class OrderJpaRepositoryTest extends BaseJpaRepositoryTest {

    @Autowired
    OrderJpaRepository orderJpaRepository;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    void test_save() {
        assertDoesNotThrow(this::saveOrder);
    }

    @Test
    void test_find() {
        Long orderId = saveOrder();
        Optional<OrderEntity> optionalOrder = orderJpaRepository.findById(orderId);
        assertThat(optionalOrder).isNotEmpty();
    }

    private Long saveOrder() {
        MemberEntity member = new MemberEntity("testName", Grade.BASIC);
        MemberEntity save = memberJpaRepository.save(member);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCreatedBy("createBy");
        orderEntity.setStatus(OrderStatus.ORDER);
        orderEntity.setMember(save);
        OrderEntity save1 = orderJpaRepository.save(orderEntity);
        return save1.getId();
    }
}