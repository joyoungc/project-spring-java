package io.joyoungc.infrastructure.persistence.repository;

import io.joyoungc.data.jpa.domain.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/***
 * Created by Aiden Jeong on 2022.02.24
 */
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
}
