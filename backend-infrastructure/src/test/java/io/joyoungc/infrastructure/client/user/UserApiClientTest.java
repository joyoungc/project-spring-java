package io.joyoungc.infrastructure.client.user;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.joyoungc.infrastructure.constant.Profiles;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(initializers = ConfigDataApplicationContextInitializer.class, classes = {UserApiConfiguration.class})
@ActiveProfiles(Profiles.TEST)
class UserApiClientTest {

    private static final WireMockServer server = new WireMockServer(wireMockConfig().dynamicPort());

    @DynamicPropertySource
    static void clientProperties(DynamicPropertyRegistry registry) {
        server.start();
        registry.add("client.user-api.endpoint", server::baseUrl);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Autowired
    UserApiClient userApiClient;

    @Test
    void getUser() {
        server.stubFor(get(urlPathEqualTo("/api/v1/persons"))
                .withQueryParam("_locale", equalTo("ko_KR"))
                .withQueryParam("_quantity", equalTo("2"))
                .willReturn(okJson("""
                        {"status":"OK","code":200,"locale":"ko_KR","total":2,
                         "data":[{"id":1,"firstname":"동윤"},{"id":2,"firstname":"정민"}]}
                        """)));

        ResponseUser user = userApiClient.getUser("ko_KR", 2);

        assertThat(user.getStatus()).isEqualTo("OK");
        assertThat(user.getCode()).isEqualTo(200);
        assertThat(user.getLocale()).isEqualTo("ko_KR");
        assertThat(user.getTotal()).isEqualTo(2);
        assertThat(user.getData()).extracting(ResponseUser.DataItem::getFirstname)
                .containsExactly("동윤", "정민");
        server.verify(1, getRequestedFor(urlPathEqualTo("/api/v1/persons"))
                .withQueryParam("_locale", equalTo("ko_KR"))
                .withQueryParam("_quantity", equalTo("2")));
    }
}
