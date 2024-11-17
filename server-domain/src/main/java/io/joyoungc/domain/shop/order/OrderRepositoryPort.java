package io.joyoungc.domain.shop.order;

public interface OrderRepositoryPort {
    Order save(Order order);

    Order findById(Long orderId);
}
