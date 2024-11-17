package io.joyoungc.api.order;

import io.joyoungc.api.order.response.OrderResponse;
import io.joyoungc.domain.shop.member.Grade;
import io.joyoungc.domain.shop.member.Member;
import io.joyoungc.domain.shop.member.MemberRepositoryPort;
import io.joyoungc.domain.shop.order.DiscountPolicy;
import io.joyoungc.domain.shop.order.Order;
import io.joyoungc.domain.shop.order.OrderRepositoryPort;
import io.joyoungc.domain.shop.product.Product;
import io.joyoungc.domain.shop.product.ProductRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;

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
        Long orderId = 100L;
        LocalDateTime orderDate = LocalDateTime.of(2024, 8, 25, 10, 0);
        long discountPrice = 1000L;
        Member member = new Member("이름", Grade.VIP);
        Product product = new Product("상품", 10000L);

        given(memberRepositoryPort.findById(1L)).willReturn(member);
        given(productRepositoryPort.findById(2L)).willReturn(product);
        given(discountPolicy.getDiscountPrice(member, product)).willReturn(discountPrice);

        Order responseOrder = new Order(member, product, discountPrice, orderDate);
        responseOrder.setId(orderId);

        given(orderRepositoryPort.save(
                argThat(o -> o.getMember().equals(member) &&
                        o.getProduct().equals(product) &&
                        o.getDiscountPrice().equals(discountPrice) &&
                        o.getOrderDate().equals(orderDate))))
                .willReturn(responseOrder);

        // when
        OrderResponse orderResponse = orderService.createOrder(1L, 2L, orderDate);

        // then
        assertThat(orderResponse)
                .isNotNull()
                .extracting(
                        OrderResponse::getOrderId,
                        OrderResponse::getDiscountPrice,
                        OrderResponse::getProductName
                )
                .doesNotContainNull()
                .containsExactly(orderId, 1000L, product.getName());
    }
}