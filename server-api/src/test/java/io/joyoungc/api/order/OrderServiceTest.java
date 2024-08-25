package io.joyoungc.api.order;

import io.joyoungc.api.order.response.OrderResponse;
import io.joyoungc.domain.shop.member.Grade;
import io.joyoungc.domain.shop.member.Member;
import io.joyoungc.domain.shop.member.MemberRepositoryPort;
import io.joyoungc.domain.shop.order.DiscountPolicy;
import io.joyoungc.domain.shop.order.FixedDiscountPolicy;
import io.joyoungc.domain.shop.order.Order;
import io.joyoungc.domain.shop.order.OrderRepositoryPort;
import io.joyoungc.domain.shop.product.Product;
import io.joyoungc.domain.shop.product.ProductRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@Slf4j
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    MemberRepositoryPort memberRepositoryPort;

    @Mock
    ProductRepositoryPort productRepositoryPort;

    @Mock
    OrderRepositoryPort orderRepositoryPort;

    @Mock
    DiscountPolicy discountPolicy;

    @Test
    void test_createOrder() {
        // given
        Long mockOrderId = 100L;
        Member member = new Member("이름", Grade.VIP);
        member.setName("이름");
        member.setGrade(Grade.VIP);
        Mockito.when(memberRepositoryPort.findById(1L)).thenReturn(member);

        Product product = new Product("상품", 10000L);
        Mockito.when(productRepositoryPort.findById(2L)).thenReturn(product);
        Mockito.when(discountPolicy.getDiscountPrice(member, product)).thenReturn(0L);

        Order order = new Order();
        order.setId(mockOrderId);
        order.setDiscountPrice(0L);
        order.setProduct(product);
        Mockito.when(orderRepositoryPort.save(any())).thenReturn(order);

        // when
        OrderResponse orderResponse =
                orderService.createOrder(1L, 2L, LocalDateTime.of(2024, 8, 25, 10, 0));

        // then
        assertThat(orderResponse).isNotNull();
        assertThat(orderResponse.getOrderId()).isEqualTo(mockOrderId);
        assertThat(orderResponse.getProductName()).isEqualTo(product.getName());
    }
}