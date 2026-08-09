package io.joyoungc.api.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.joyoungc.api.BaseServerApiIntegrationTest;
import io.joyoungc.api.product.request.CreateProductRequest;
import io.joyoungc.api.product.response.ProductResponse;
import io.joyoungc.domain.model.common.CommonResponse;
import io.joyoungc.domain.enums.ResponseCode;
import io.joyoungc.infrastructure.constant.Profiles;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureWebMvc
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class,
        MongoRepositoriesAutoConfiguration.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles(Profiles.TEST)
class ProductControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }


    @Test
    @DisplayName("제품 등록 API 테스트")
    void test_createProduct() throws Exception {
        // given
        CreateProductRequest request = new CreateProductRequest();
        request.setName("노트북");
        request.setPrice(1500000L);

        // when
        String response = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        CommonResponse commonResponse = objectMapper.readValue(response, CommonResponse.class);
        assertThat(commonResponse.getCode()).isEqualTo(ResponseCode.SUCCESS.getCode());
        assertThat(commonResponse.getMessage()).contains("productId");
    }

    @Test
    @DisplayName("제품 조회 API 테스트")
    void test_getProduct() throws Exception {
        // given - 먼저 제품을 등록
        CreateProductRequest createRequest = new CreateProductRequest();
        createRequest.setName("키보드");
        createRequest.setPrice(100000L);

        String createResponse = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CommonResponse createResult = objectMapper.readValue(createResponse, CommonResponse.class);
        String productId = createResult.getMessage().split(" : ")[1];

        // when - 등록된 제품을 조회
        String getResponse = mockMvc.perform(get("/api/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        ProductResponse productResponse = objectMapper.readValue(getResponse, ProductResponse.class);
        assertThat(productResponse.getId()).isEqualTo(Long.parseLong(productId));
        assertThat(productResponse.getName()).isEqualTo("키보드");
        assertThat(productResponse.getPrice()).isEqualTo(100000L);
    }

    @Test
    @DisplayName("제품 등록 시 유효성 검증 테스트 - 제품명 누락")
    void test_createProduct_validation_name_blank() throws Exception {
        // given
        CreateProductRequest request = new CreateProductRequest();
        request.setName("");
        request.setPrice(1500000L);

        // when & then
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("제품 등록 시 유효성 검증 테스트 - 가격 0 이하")
    void test_createProduct_validation_price_negative() throws Exception {
        // given
        CreateProductRequest request = new CreateProductRequest();
        request.setName("마우스");
        request.setPrice(-1000L);

        // when & then
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}