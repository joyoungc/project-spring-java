package io.joyoungc.infrastructure.persistence;

import io.joyoungc.domain.common.constant.CommonError;
import io.joyoungc.domain.common.exception.ApplicationException;
import io.joyoungc.domain.product.Product;
import io.joyoungc.domain.product.ProductRepositoryPort;
import io.joyoungc.infrastructure.persistence.configuration.PersistenceAdapter;
import io.joyoungc.infrastructure.persistence.entity.ProductEntity;
import io.joyoungc.infrastructure.persistence.mapper.ProductMapper;
import io.joyoungc.infrastructure.persistence.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@PersistenceAdapter
@RequiredArgsConstructor
public class ProductRepositoryPersistenceAdapter implements ProductRepositoryPort {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Product findById(Long productId) {
        ProductEntity productEntity = productJpaRepository.findById(productId)
                .orElseThrow(() -> new ApplicationException(CommonError.COMMON_NOT_FOUND));
        return ProductMapper.INSTANCE.toProduct(productEntity);
    }

    @Override
    @Transactional
    public Long save(Product product) {
        ProductEntity save = productJpaRepository.save(ProductMapper.INSTANCE.toProductEntity(product));
        return save.getId();
    }
}
