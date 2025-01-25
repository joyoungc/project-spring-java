package io.joyoungc.infrastructure.persistence.shop;


import io.joyoungc.application.exception.ApplicationException;
import io.joyoungc.domain.enums.CommonError;
import io.joyoungc.domain.model.order.Order;
import io.joyoungc.application.output.OrderRepositoryPort;
import io.joyoungc.infrastructure.persistence.configuration.PersistenceAdapter;
import io.joyoungc.infrastructure.persistence.shop.entity.OrderEntity;
import io.joyoungc.infrastructure.persistence.shop.mapper.OrderMapper;
import io.joyoungc.infrastructure.persistence.shop.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class OrderRepositoryPersistenceAdapter implements OrderRepositoryPort {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = OrderMapper.INSTANCE.toOrderEntity(order);
        OrderEntity save = orderJpaRepository.save(orderEntity);
        return OrderMapper.INSTANCE.toOrder(save);
    }

    @Override
    public Order findById(Long orderId) {
        OrderEntity orderEntity = orderJpaRepository.findById(orderId)
                .orElseThrow(() -> new ApplicationException(CommonError.COMMON_NOT_FOUND));
        return OrderMapper.INSTANCE.toOrder(orderEntity);
    }
}
