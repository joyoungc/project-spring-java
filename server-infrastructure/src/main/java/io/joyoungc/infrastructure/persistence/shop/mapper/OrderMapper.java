package io.joyoungc.infrastructure.persistence.shop.mapper;

import io.joyoungc.domain.shop.order.Order;
import io.joyoungc.infrastructure.persistence.shop.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderEntity toOrderEntity(Order order);

    @Mapping(target = "member", ignore = true)
//    @Mapping(target = "product", ignore = true)
    Order toOrder(OrderEntity orderEntity);

    List<Order> toOrderList(List<OrderEntity> orderEntities);
}
