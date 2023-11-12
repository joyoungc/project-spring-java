package io.joyoungc.infrastructure.persistence;


import io.joyoungc.data.jpa.domain.OrderEntity;
import io.joyoungc.domain.common.constant.CommonError;
import io.joyoungc.domain.common.exception.ApplicationException;
import io.joyoungc.domain.order.Order;
import io.joyoungc.domain.order.OrderRepository;
import io.joyoungc.infrastructure.persistence.mapper.OrderMapper;
import io.joyoungc.infrastructure.persistence.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements OrderRepository {

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
