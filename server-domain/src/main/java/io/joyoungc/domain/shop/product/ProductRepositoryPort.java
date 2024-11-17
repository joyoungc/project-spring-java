package io.joyoungc.domain.shop.product;

public interface ProductRepositoryPort {

    Product findById(Long productId);

    Long save(Product product);
}
