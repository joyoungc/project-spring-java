package io.joyoungc.api.order;

import io.joyoungc.api.common.configuration.ServerApiConfig;
import io.joyoungc.domain.member.Grade;
import io.joyoungc.domain.member.Member;
import io.joyoungc.domain.member.MemberRepositoryPort;
import io.joyoungc.domain.order.OrderRepositoryPort;
import io.joyoungc.domain.product.Product;
import io.joyoungc.domain.product.ProductRepositoryPort;
import io.joyoungc.infrastructure.constant.Profiles;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@Slf4j
@SpringJUnitConfig(classes = {OrderService.class, ServerApiConfig.class})
@ActiveProfiles(Profiles.TEST)
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @MockBean
    MemberRepositoryPort memberRepositoryPort;

    @MockBean
    ProductRepositoryPort productRepositoryPort;

    @MockBean
    OrderRepositoryPort orderRepositoryPort;

    @Test
    void create_order() {
        // given
        Member member = new Member("이름", Grade.VIP);
        member.setName("이름");
        member.setGrade(Grade.VIP);
        Mockito.when(memberRepositoryPort.findById(1L)).thenReturn(member);

        Product product = new Product("상품", 10000L);
        Mockito.when(productRepositoryPort.findById(2L)).thenReturn(product);

        Mockito.when(orderRepositoryPort.save(any())).thenReturn(100L);

        // when
        Long orderId = orderService.createOrder(1L, 2L);
        log.debug("## order : {}", orderId);

        // then
        assertThat(orderId).isNotNull();
    }
}