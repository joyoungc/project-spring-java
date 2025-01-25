package io.joyoungc.infrastructure.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.joyoungc.infrastructure.client.httpapi.HttpApiClient;
import io.joyoungc.infrastructure.client.httpapi.HttpApiConfiguration;
import io.joyoungc.infrastructure.constant.Profiles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.HttpClientErrorException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringJUnitConfig(initializers = ConfigDataApplicationContextInitializer.class,
        classes = {HttpApiConfiguration.class})
@ActiveProfiles(Profiles.TEST)
@TestPropertySource(properties = {"client.http-api.endpoint=http://localhost:9000"})
@WireMockTest(httpPort = 9000)
class HttpApiClientWireMockTest {

    @Autowired
    HttpApiClient httpApiClient;

    @Test
    void ok_request() {
        // given
        stubFor(
                WireMock.get(anyUrl()).willReturn(ok()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("\"message\": \"success\""))
        );

        // when
        String response = httpApiClient.get("/any-url");

        // then
        assertThat(response).isEqualTo("\"message\": \"success\"");
    }

    @Test
    void bad_request() {
        // given
        String url = "/api/any-url";
        stubFor(
                WireMock.get(url).willReturn(badRequest()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("\"message\": \"bad request\""))
        );

        // then
        assertThatThrownBy(() -> httpApiClient.get("any-url"))
                .isInstanceOf(HttpClientErrorException.class)
                .hasMessageContaining("bad request")
                .satisfies(e -> {
                    HttpClientErrorException clientErrorException = (HttpClientErrorException) e;
                    assertThat(clientErrorException.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
                });;
    }

}
