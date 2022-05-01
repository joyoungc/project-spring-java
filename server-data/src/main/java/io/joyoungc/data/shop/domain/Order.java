package io.joyoungc.data.shop.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/***
 * Created by Aiden Jeong on 2021.12.12
 */
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class Order extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne
    @JoinColumn(name = "product_id")
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

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public void updateOrder(OrderStatus status) {
        this.status = status;
    }

}
