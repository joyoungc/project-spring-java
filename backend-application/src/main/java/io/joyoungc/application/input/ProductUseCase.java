package io.joyoungc.application.input;

import io.joyoungc.domain.model.product.Product;

public interface ProductUseCase {

    Long createProduct(Product product);

    Product getProduct(Long productId);
}