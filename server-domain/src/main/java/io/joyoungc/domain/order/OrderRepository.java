package io.joyoungc.domain.order;

public interface OrderRepository {
    Long save(Order order);

    Order findById(Long orderId);
}
