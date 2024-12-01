package io.joyoungc.application.output;

import io.joyoungc.domain.model.order.Order;

public interface OrderRepositoryPort {
    Order save(Order order);

    Order findById(Long orderId);
}
