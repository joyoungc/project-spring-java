package io.joyoungc.api.order.dto;

import io.joyoungc.data.shop.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/***
 * Created by Aiden Jeong on 2022.04.03
 */
public class OrderDto {

    @Getter @Setter
    public static class RequestUpdate {
        @NotNull
        private Long orderId;
        @NotNull
        private OrderStatus status;
    }

    @Getter @Setter
    public static class RequestCreate {
        @NotNull
        private Long memberId;
        @NotNull
        private Long productId;
        private OrderStatus status;
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseOrder {
        private Long orderId;
        private Long discountPrice;
        private String productName;
    }

}
