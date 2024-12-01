package io.joyoungc.domain.model.order;


import io.joyoungc.domain.model.member.Member;
import io.joyoungc.domain.model.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Order {

    private Long id;

    private Member member;

    private Product product;

    private Long discountPrice;

    // orderItems 1:1

    public Order(Member member, Product product, Long discountPrice, LocalDateTime orderDate) {
        this.member = member;
        this.product = product;
        this.discountPrice = discountPrice;
        this.orderDate = orderDate;
    }

    // delivery 1:1

    private LocalDateTime orderDate;

    private OrderStatus status;

    public void updateOrder(OrderStatus status) {
        this.status = status;
    }
}
