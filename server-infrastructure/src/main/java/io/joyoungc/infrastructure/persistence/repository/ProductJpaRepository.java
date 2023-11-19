package io.joyoungc.infrastructure.persistence.repository;

import io.joyoungc.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/***
 * Created by Aiden Jeong on 2022.02.24
 */
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
}
