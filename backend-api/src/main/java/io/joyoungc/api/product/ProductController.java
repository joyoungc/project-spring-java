package io.joyoungc.api.product;

import io.joyoungc.api.product.mapper.ProductMapper;
import io.joyoungc.api.product.request.CreateProductRequest;
import io.joyoungc.api.product.response.ProductResponse;
import io.joyoungc.application.input.ProductUseCase;
import io.joyoungc.domain.enums.ResponseCode;
import io.joyoungc.domain.model.common.CommonResponse;
import io.joyoungc.domain.model.product.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductUseCase productService;

    /**
     * 제품 등록
     *
     * @param dto
     * @return
     */
    @PostMapping
    public CommonResponse createProduct(@RequestBody @Valid CreateProductRequest dto) {
        Product product = ProductMapper.INSTANCE.toProduct(dto);
        Long productId = productService.createProduct(product);
        return CommonResponse.of(ResponseCode.SUCCESS, "productId : " + productId);
    }

    /**
     * 제품 조회
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable("id") Long id) {
        return ProductMapper.INSTANCE.toProductResponse(productService.getProduct(id));
    }
}