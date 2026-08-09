package io.joyoungc.api.product.mapper;

import io.joyoungc.api.product.request.CreateProductRequest;
import io.joyoungc.api.product.response.ProductResponse;
import io.joyoungc.domain.model.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toProduct(CreateProductRequest request);

    ProductResponse toProductResponse(Product product);

    List<ProductResponse> toProductResponseList(List<Product> products);
}