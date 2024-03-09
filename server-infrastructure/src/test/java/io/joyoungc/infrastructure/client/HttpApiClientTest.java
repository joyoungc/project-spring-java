package io.joyoungc.infrastructure.client;

import io.joyoungc.infrastructure.client.httpapi.HttpApiClient;
import io.joyoungc.infrastructure.client.httpapi.HttpApiConfiguration;
import io.joyoungc.infrastructure.constant.Profiles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(initializers = ConfigDataApplicationContextInitializer.class, classes = {HttpApiConfiguration.class})
@ActiveProfiles(Profiles.TEST)
class HttpApiClientTest {

    @Autowired
    HttpApiClient httpApiClient;

    @Test
    void test_get() {
        // when
        String response = httpApiClient.get("activity");

        // then
        assertThat(response).contains("activity");
    }
}