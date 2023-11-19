package io.joyoungc.api.order.request;


import io.joyoungc.domain.order.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateOrderRequest {
    @NotNull
    private Long memberId;
    @NotNull
    private Long productId;
    private OrderStatus status;
}
