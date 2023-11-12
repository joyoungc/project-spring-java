package io.joyoungc.infrastructure.persistence.mapper;

import io.joyoungc.data.jpa.domain.ProductEntity;
import io.joyoungc.domain.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toProduct(ProductEntity entity);

    ProductEntity toProductEntity(Product product);
}
