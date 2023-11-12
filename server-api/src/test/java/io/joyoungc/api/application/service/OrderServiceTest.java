package io.joyoungc.api.application.service;

import io.joyoungc.api.TestJpaConfig;
import io.joyoungc.api.service.OrderService;
import io.joyoungc.domain.member.Grade;
import io.joyoungc.domain.member.Member;
import io.joyoungc.domain.member.MemberRepository;
import io.joyoungc.domain.product.Product;
import io.joyoungc.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RequiredArgsConstructor
@TestJpaConfig
class OrderServiceTest {

    private final OrderService orderService;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Test
    void create_order() {
        Member member = new Member();
        member.setName("이름");
        member.setGrade(Grade.VIP);
        Long memberId = memberRepository.save(member);
        Long productId = productRepository.save(new Product("상품", 10000L));
        Long orderId = orderService.createOrder(memberId, productId);
        log.debug("## order : {}", orderId);
        assertThat(orderId).isNotNull();
    }
}