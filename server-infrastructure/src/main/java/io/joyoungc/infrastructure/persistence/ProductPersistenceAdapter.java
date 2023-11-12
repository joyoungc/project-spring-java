package io.joyoungc.infrastructure.persistence;

import io.joyoungc.data.jpa.domain.ProductEntity;
import io.joyoungc.domain.common.constant.CommonError;
import io.joyoungc.domain.common.exception.ApplicationException;
import io.joyoungc.domain.product.Product;
import io.joyoungc.domain.product.ProductRepository;
import io.joyoungc.infrastructure.persistence.mapper.ProductMapper;
import io.joyoungc.infrastructure.persistence.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Product findById(Long productId) {
        ProductEntity productEntity = productJpaRepository.findById(productId)
                .orElseThrow(() -> new ApplicationException(CommonError.COMMON_NOT_FOUND));
        return ProductMapper.INSTANCE.toProduct(productEntity);
    }

    @Override
    public Long save(Product product) {
        ProductEntity save = productJpaRepository.save(ProductMapper.INSTANCE.toProductEntity(product));
        return save.getId();
    }
}
