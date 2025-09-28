package io.joyoungc.application.input;

import io.joyoungc.application.output.ProductRepositoryPort;
import io.joyoungc.domain.model.product.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {

    private final ProductRepositoryPort productRepositoryPort;

    @Override
    @Transactional
    public Long createProduct(Product product) {
        return productRepositoryPort.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProduct(Long productId) {
        return productRepositoryPort.findById(productId);
    }
}