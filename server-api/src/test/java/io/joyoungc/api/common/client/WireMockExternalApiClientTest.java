package io.joyoungc.api.common.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

class WireMockExternalApiClientTest {
    HttpApiClient httpApiClient;
    WireMockServer wireMockServer = new WireMockServer(9000);

    @BeforeEach
    void setUp() {
        httpApiClient = new ExternalApiClient("http://localhost:9000");
        wireMockServer.start();
    }

    @AfterEach
    void cleanUp() {
        wireMockServer.resetAll();
        wireMockServer.stop();
    }

    @Test
    void ok_request() {
        // given
        wireMockServer.stubFor(
                WireMock.get(anyUrl()).willReturn(ok()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("\"message\": \"success\""))
        );

        // when
        String response = httpApiClient.get("/any-url");

        // then
        assertThat(response).isEqualTo("\"message\": \"success\"");

        wireMockServer.resetAll();
    }

    @Test
    void bad_request() {
        // given
        wireMockServer.stubFor(
                WireMock.get(anyUrl()).willReturn(badRequest()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("\"message\": \"bad request\""))
        );

        // when
        String response = httpApiClient.get("/any-url");

        // then
        assertThat(response).isNull();
    }

    @Test
    void exception() {
        // when
        httpApiClient.get("/illegal-url\\**");
    }
}
