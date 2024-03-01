package io.joyoungc.infrastructure.persistence;


import io.joyoungc.domain.common.constant.CommonError;
import io.joyoungc.domain.common.exception.ApplicationException;
import io.joyoungc.domain.order.Order;
import io.joyoungc.domain.order.OrderRepositoryPort;
import io.joyoungc.infrastructure.persistence.configuration.PersistenceAdapter;
import io.joyoungc.infrastructure.persistence.entity.OrderEntity;
import io.joyoungc.infrastructure.persistence.mapper.OrderMapper;
import io.joyoungc.infrastructure.persistence.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class OrderRepositoryPersistenceAdapter implements OrderRepositoryPort {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Long save(Order order) {
        OrderEntity orderEntity = OrderMapper.INSTANCE.toOrderEntity(order);
        OrderEntity save = orderJpaRepository.save(orderEntity);
        return save.getId();
    }

    @Override
    public Order findById(Long orderId) {
        OrderEntity orderEntity = orderJpaRepository.findById(orderId)
                .orElseThrow(() -> new ApplicationException(CommonError.COMMON_NOT_FOUND));
        return OrderMapper.INSTANCE.toOrder(orderEntity);
    }
}
