package io.joyoungc.api.common.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ExternalApiClient implements HttpApiClient {

    private final String host;

    public ExternalApiClient(String host) {
        this.host = host;
    }

    @Override
    public String get(String url) {
        String responseBody = "fail";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(host + url);
            responseBody = httpClient.execute(request, response1 -> {
                if (response1.getCode() == HttpServletResponse.SC_OK) {
                    return EntityUtils.toString(response1.getEntity());
                }
                return null;
            });
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return responseBody;
    }
}
