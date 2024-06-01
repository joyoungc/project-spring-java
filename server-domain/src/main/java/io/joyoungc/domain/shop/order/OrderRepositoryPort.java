package io.joyoungc.domain.shop.order;

public interface OrderRepositoryPort {
    Long save(Order order);

    Order findById(Long orderId);
}
