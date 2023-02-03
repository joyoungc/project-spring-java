package io.joyoungc.api.common.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;

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

    @Test
    void ok_request() throws IOException {
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
    }
}
