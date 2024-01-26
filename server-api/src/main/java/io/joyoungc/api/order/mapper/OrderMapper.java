package io.joyoungc.api.order.mapper;

import io.joyoungc.api.order.response.OrderResponse;
import io.joyoungc.domain.order.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "productName", source = "order.product.name")
    @Mapping(target = "orderId", source = "order.id")
    OrderResponse toOrderResponse(Order order);

    List<OrderResponse> toOrderResponseList(List<Order> orders);
}
