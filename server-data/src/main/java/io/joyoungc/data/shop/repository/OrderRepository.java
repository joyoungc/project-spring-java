package io.joyoungc.data.shop.repository;

import io.joyoungc.data.shop.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/***
 * Created by Aiden Jeong on 2022.02.24
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
