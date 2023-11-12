package io.joyoungc.api.infrastructure.repository;

import io.joyoungc.data.jpa.domain.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/***
 * Created by Aiden Jeong on 2022.02.24
 */
public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
}
