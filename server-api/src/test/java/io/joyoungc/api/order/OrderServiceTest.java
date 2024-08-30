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
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

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
        LocalDateTime mcokLocalDateTime = LocalDateTime.of(2024, 8, 25, 10, 0);
        long mockDiscountPrice = 1000L;
        Member mockMember = new Member("이름", Grade.VIP);
        Product mockProduct = new Product("상품", 10000L);

        when(memberRepositoryPort.findById(1L)).thenReturn(mockMember);
        when(productRepositoryPort.findById(2L)).thenReturn(mockProduct);
        when(discountPolicy.getDiscountPrice(mockMember, mockProduct)).thenReturn(mockDiscountPrice);

        Order responseOrder = new Order(mockMember, mockProduct, mockDiscountPrice, mcokLocalDateTime);
        responseOrder.setId(mockOrderId);

        when(orderRepositoryPort.save(
                argThat(o -> o.getMember().equals(mockMember) &&
                        o.getProduct().equals(mockProduct) &&
                        o.getDiscountPrice().equals(mockDiscountPrice) &&
                        o.getOrderDate().equals(mcokLocalDateTime))))
                .thenReturn(responseOrder);

        // when
        OrderResponse orderResponse = orderService.createOrder(1L, 2L, mcokLocalDateTime);

        // then
        assertThat(orderResponse)
                .isNotNull()
                .extracting(
                        OrderResponse::getOrderId,
                        OrderResponse::getDiscountPrice,
                        OrderResponse::getProductName
                )
                .doesNotContainNull()
                .containsExactly(mockOrderId, 1000L, mockProduct.getName());
    }
}