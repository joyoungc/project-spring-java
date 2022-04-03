package io.joyoungc.api.order;

import io.joyoungc.api.TestJpaConfig;
import io.joyoungc.api.member.service.MemberService;
import io.joyoungc.api.order.dto.OrderDto;
import io.joyoungc.data.shop.domain.Grade;
import io.joyoungc.data.shop.domain.Member;
import io.joyoungc.data.shop.domain.OrderStatus;
import io.joyoungc.data.shop.domain.Product;
import io.joyoungc.data.shop.repository.MemberRepository;
import io.joyoungc.data.shop.repository.ProductRepository;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static io.joyoungc.api.TestUtils.createUrlWithPort;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/***
 * Created by Aiden Jeong on 2022.04.03
 */
@Slf4j
@TestJpaConfig
@RequiredArgsConstructor
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberService memberService;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    private static final String API_ENDPOINT = "/api/orders";

    static Long memberId;
    static Long productId;

    // @BeforeEach
    private void init() {
        Member member = memberRepository.save(new Member("이름", Grade.VIP));
        memberId = member.getId();
        Product product = productRepository.save(new Product("상품", 10000L));
        productId = product.getId();
    }

    @Test
    @DisplayName("주문 등록")
    @Order(1)
    void createOrder() {
        init();
        String url = createUrlWithPort(API_ENDPOINT, port);
        OrderDto.RequestCreate create = new OrderDto.RequestCreate();
        create.setMemberId(memberId);
        create.setProductId(productId);
        create.setStatus(OrderStatus.ORDER);

        given().log().all()
                .contentType(ContentType.JSON)
                .body(create)
                .when().post(url)
                .then().log().body()
                .statusCode(200);
    }

    @Test
    @DisplayName("회원 주문 조회")
    @Order(2)
    void getMemberOrders() {
        String url = createUrlWithPort(API_ENDPOINT, port) + "?memberId=" + memberId;

        // given
        Response response = given().log().all()
                // when
                .when().get(url)
                // then
                .then()
                .log().body()
                .statusCode(200)
                .extract().response();

        List<OrderDto.ResponseOrder> list = response.jsonPath().getList(".", OrderDto.ResponseOrder.class);

        assertThat(list).isNotEmpty().element(0).satisfies(
                c -> {
                    assertThat(c.getOrderId()).isNotNull();
                    assertThat(c.getProductName()).isEqualTo("상품");
                    assertThat(c.getDiscountPrice()).isNotNull();
                }
        );
    }

}
