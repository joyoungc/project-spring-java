package io.joyoungc.infrastructure.persistence.mapper;

import io.joyoungc.domain.product.Product;
import io.joyoungc.infrastructure.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toProduct(ProductEntity entity);

    ProductEntity toProductEntity(Product product);
}
