package io.joyoungc.data.domain.shop;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

/***
 * Created by Aiden Jeong on 2021.12.12
 */
@Entity
@Table(name = "orders")
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // orderItems 1:1

    // delivery 1:1

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

}
