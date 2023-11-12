package io.joyoungc.api.request;

import io.joyoungc.domain.order.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateOrderRequest {
    @NotNull
    private Long orderId;
    @NotNull
    private OrderStatus status;
}
