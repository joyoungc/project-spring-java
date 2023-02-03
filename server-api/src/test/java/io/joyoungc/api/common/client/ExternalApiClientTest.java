package io.joyoungc.api.common.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ExternalApiClientTest {
    ExternalApiClient externalApiClient;

    @BeforeEach
    void setUp() {
        externalApiClient = new ExternalApiClient("https://www.boredapi.com");
    }

    @Test
    void get() {
        // when
        String response = externalApiClient.get("/api/activity");

        // then
        assertThat(response).isNotEqualTo("fail");
    }
}