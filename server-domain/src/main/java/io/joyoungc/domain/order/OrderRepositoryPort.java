package io.joyoungc.domain.order;

public interface OrderRepositoryPort {
    Long save(Order order);

    Order findById(Long orderId);
}
