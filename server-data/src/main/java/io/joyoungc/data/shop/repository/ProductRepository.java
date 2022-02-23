package io.joyoungc.data.shop.repository;

import io.joyoungc.data.shop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/***
 * Created by Aiden Jeong on 2022.02.24
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}
