package io.joyoungc.api.order;

import io.joyoungc.api.order.mapper.OrderMapper;
import io.joyoungc.api.order.request.UpdateOrderRequest;
import io.joyoungc.api.order.response.OrderResponse;
import io.joyoungc.domain.member.Member;
import io.joyoungc.domain.member.MemberRepositoryPort;
import io.joyoungc.domain.order.DiscountPolicy;
import io.joyoungc.domain.order.Order;
import io.joyoungc.domain.order.OrderRepositoryPort;
import io.joyoungc.domain.product.Product;
import io.joyoungc.domain.product.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/***
 * Created by Aiden Jeong on 2022.02.24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepositoryPort memberRepositoryPort;
    private final ProductRepositoryPort productRepository;
    private final DiscountPolicy discountPolicy;
    private final OrderRepositoryPort orderRepository;

    @Transactional
    public Long createOrder(Long memberId, Long productId) {
        Member member = memberRepositoryPort.findById(memberId);

        Product product = productRepository.findById(productId);

        long discountPrice = discountPolicy.getDiscountPrice(member, product);

        Order order = new Order(member, product, discountPrice, LocalDateTime.now());

        Long orderId = orderRepository.save(order);
        log.info("## Order created : {}", orderId);
        return orderId;
    }

    @Transactional
    public void updateOrder(Long orderId, UpdateOrderRequest requestUpdate) {
        Order order = orderRepository.findById(orderId);
        order.updateOrder(requestUpdate.getStatus());
    }

    /**
     * TODO) Paging 이용할것..
     *
     * @param memberId
     * @return
     */
    @Transactional(readOnly = true)
    public List<OrderResponse> getMemberOrders(Long memberId) {
        Member member = memberRepositoryPort.findById(memberId);
        List<Order> orders = member.getOrders();
        List<OrderResponse> resultList = OrderMapper.INSTANCE.toOrderResponseList(orders);
        return resultList;
    }
}
