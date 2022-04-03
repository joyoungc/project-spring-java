package io.joyoungc.api.order.service;

import io.joyoungc.api.TestJpaConfig;
import io.joyoungc.data.shop.domain.Grade;
import io.joyoungc.data.shop.domain.Member;
import io.joyoungc.data.shop.domain.Product;
import io.joyoungc.data.shop.repository.MemberRepository;
import io.joyoungc.data.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@RequiredArgsConstructor
@TestJpaConfig
class OrderServiceTest {

    private final OrderService orderService;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Test
    void createOrder() {
        Member member = memberRepository.save(new Member("이름", Grade.VIP));
        Product product = productRepository.save(new Product("상품", 10000L));
        Long orderId = orderService.createOrder(member.getId(), product.getId());
        log.debug("## order : {}", orderId);
        Assertions.assertThat(orderId).isNotNull();
    }
}