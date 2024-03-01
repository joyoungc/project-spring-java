package io.joyoungc.api.order;

import io.joyoungc.api.BaseServerApiIntegrationTest;
import io.joyoungc.domain.member.Grade;
import io.joyoungc.domain.member.Member;
import io.joyoungc.domain.member.MemberRepositoryPort;
import io.joyoungc.domain.product.Product;
import io.joyoungc.domain.product.ProductRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class OrderServiceTest extends BaseServerApiIntegrationTest {

    @Autowired
    OrderService orderService;
    @Autowired
    MemberRepositoryPort memberRepositoryPort;
    @Autowired
    ProductRepositoryPort productRepository;

    @Test
    void create_order() {
        Member member = new Member();
        member.setName("이름");
        member.setGrade(Grade.VIP);
        Long memberId = memberRepositoryPort.save(member);
        Long productId = productRepository.save(new Product("상품", 10000L));
        Long orderId = orderService.createOrder(memberId, productId);
        log.debug("## order : {}", orderId);
        assertThat(orderId).isNotNull();
    }
}