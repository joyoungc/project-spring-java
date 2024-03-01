package io.joyoungc.api.order;

import io.joyoungc.api.BaseServerApiIntegrationTest;
import io.joyoungc.api.order.request.CreateOrderRequest;
import io.joyoungc.api.order.response.OrderResponse;
import io.joyoungc.domain.member.Grade;
import io.joyoungc.domain.member.Member;
import io.joyoungc.domain.member.MemberRepositoryPort;
import io.joyoungc.domain.order.OrderStatus;
import io.joyoungc.domain.product.Product;
import io.joyoungc.domain.product.ProductRepositoryPort;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static io.joyoungc.api.TestUtils.createUrlWithPort;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/***
 * Created by Aiden Jeong on 2022.04.03
 */
class OrderIntegrationTest extends BaseServerApiIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    MemberRepositoryPort memberRepository;

    @Autowired
    ProductRepositoryPort productRepository;

    private static final String API_ENDPOINT = "/api/orders";

    static Long memberId;
    static Long productId;

    // @BeforeEach
    private void init() {
        Member member = new Member();
        member.setName("이름");
        member.setGrade(Grade.VIP);
        memberId = memberRepository.save(member);
        productId = productRepository.save(new Product("상품", 10000L));
    }

    @Test
    @Order(1)
    void create_order() {
        init();
        String url = createUrlWithPort(API_ENDPOINT, port);
        CreateOrderRequest create = new CreateOrderRequest();
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
    @Order(2)
    void get_member_orders() {
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

        List<OrderResponse> list = response.jsonPath().getList(".", OrderResponse.class);

        assertThat(list).isNotEmpty().element(0).satisfies(
                c -> {
                    assertThat(c.getOrderId()).isNotNull();
                    assertThat(c.getProductName()).isEqualTo("상품");
                    assertThat(c.getDiscountPrice()).isNotNull();
                }
        );
    }

}
