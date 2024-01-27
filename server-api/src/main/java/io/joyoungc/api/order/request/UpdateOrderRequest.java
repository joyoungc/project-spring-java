package io.joyoungc.api.order.request;

import io.joyoungc.domain.order.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderRequest {
    @NotNull
    private Long orderId;
    @NotNull
    private OrderStatus status;
}
