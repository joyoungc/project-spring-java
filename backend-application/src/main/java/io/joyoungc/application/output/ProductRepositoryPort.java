package io.joyoungc.application.output;

import io.joyoungc.domain.model.product.Product;

public interface ProductRepositoryPort {

    Product findById(Long productId);

    Long save(Product product);
}
