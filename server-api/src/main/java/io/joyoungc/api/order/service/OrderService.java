package io.joyoungc.api.order.service;

import io.joyoungc.api.order.dto.OrderDto;
import io.joyoungc.common.CommonError;
import io.joyoungc.common.exception.ApplicationException;
import io.joyoungc.data.shop.domain.Member;
import io.joyoungc.data.shop.domain.Order;
import io.joyoungc.data.shop.domain.Product;
import io.joyoungc.data.shop.repository.MemberRepository;
import io.joyoungc.data.shop.repository.OrderRepository;
import io.joyoungc.data.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/***
 * Created by Aiden Jeong on 2022.02.24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final DiscountPolicy discountPolicy;
    private final OrderRepository orderRepository;

    @Transactional
    public Long createOrder(Long memberId, Long productId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApplicationException(CommonError.COMMON_NOT_FOUND));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ApplicationException(CommonError.COMMON_NOT_FOUND));

        long discountPrice = discountPolicy.getDiscountPrice(member, product);

        Order order = new Order(member, product, discountPrice, LocalDateTime.now());

        Order save = orderRepository.save(order);
        log.info("## Order created : {}", save);
        return save.getId();
    }

    @Transactional
    public void updateOrder(Long orderId, OrderDto.RequestUpdate requestUpdate) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ApplicationException(CommonError.COMMON_NOT_FOUND));
        order.updateOrder(requestUpdate.getStatus());
    }

    /**
     * TODO) Paging 이용할것..
     *
     * @param memberId
     * @return
     */
    @Transactional(readOnly = true)
    public List<OrderDto.ResponseOrder> getMemberOrders(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApplicationException(CommonError.COMMON_NOT_FOUND));

        List<Order> orders = member.getOrders();

        List<OrderDto.ResponseOrder> resultList = orders.stream()
                .map(order -> new OrderDto.ResponseOrder(order.getId(), order.getDiscountPrice(), order.getProduct().getName()))
                .collect(Collectors.toList());

        return resultList;
    }
}
