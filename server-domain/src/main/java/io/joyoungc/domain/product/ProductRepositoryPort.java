package io.joyoungc.domain.product;

public interface ProductRepositoryPort {

    Product findById(Long productId);

    Long save(Product product);
}
