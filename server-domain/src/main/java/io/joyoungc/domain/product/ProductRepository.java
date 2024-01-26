package io.joyoungc.domain.product;

public interface ProductRepository {

    Product findById(Long productId);

    Long save(Product product);
}
