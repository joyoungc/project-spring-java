package io.joyoungc.infrastructure.client.httpapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Slf4j
@Configuration
@EnableConfigurationProperties(HttpApiProperties.class)
@RequiredArgsConstructor
public class HttpApiConfiguration {

    private final HttpApiProperties properties;

    /**
     * RestClient for Sample API
     *
     * @return
     */
    @Bean
    public HttpApiClient httpApiClient() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofSeconds(properties.getConnectionTimeout()));
        requestFactory.setConnectionRequestTimeout(Duration.ofSeconds(properties.getConnectionTimeout()));

        RestClient restClient = RestClient.builder()
                .baseUrl(properties.getEndpoint())
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .defaultHeaders(headers -> {
                    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                })
                .build();

        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();

        return httpServiceProxyFactory.createClient(HttpApiClient.class);
    }

}
