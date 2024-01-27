package io.joyoungc.api.order.request;


import io.joyoungc.domain.order.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequest {
    @NotNull
    private Long memberId;
    @NotNull
    private Long productId;
    private OrderStatus status;
}
