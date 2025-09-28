package io.joyoungc.api.product;

import io.joyoungc.api.product.mapper.ProductMapper;
import io.joyoungc.api.product.request.CreateProductRequest;
import io.joyoungc.api.product.response.ProductResponse;
import io.joyoungc.application.input.ProductService;
import io.joyoungc.application.output.ProductRepositoryPort;
import io.joyoungc.domain.model.product.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepositoryPort productRepositoryPort;

    @Test
    void test_createProduct() {
        // given
        CreateProductRequest request = new CreateProductRequest();
        request.setName("노트북");
        request.setPrice(1500000L);

        given(productRepositoryPort.save(
                argThat(o -> o.getName().equals(request.getName()) &&
                        o.getPrice().equals(request.getPrice()))
        )).willReturn(1L);

        // when
        Long productId = productService.createProduct(ProductMapper.INSTANCE.toProduct(request));

        // then
        assertThat(productId).isNotNull().isEqualTo(1L);
    }

    @Test
    void test_getProduct() {
        // given
        long productId = 1000L;
        Product product = new Product("키보드", 100000L);
        product.setId(productId);
        given(productRepositoryPort.findById(productId)).willReturn(product);

        // when
        ProductResponse productResponse = ProductMapper.INSTANCE.toProductResponse(productService.getProduct(productId));

        // then
        assertThat(productResponse)
                .isNotNull()
                .extracting(ProductResponse::getId, ProductResponse::getName, ProductResponse::getPrice)
                .doesNotContainNull()
                .containsExactly(productId, "키보드", 100000L);
    }
}