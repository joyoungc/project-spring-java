package io.joyoungc.infrastructure.persistence.mapper;

import io.joyoungc.domain.order.Order;
import io.joyoungc.infrastructure.persistence.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderEntity toOrderEntity(Order order);

    @Mapping(target = "member", ignore = true)
//    @Mapping(target = "product", ignore = true)
    Order toOrder(OrderEntity orderEntity);

    List<Order> toOrderList(List<OrderEntity> orderEntities);
}
