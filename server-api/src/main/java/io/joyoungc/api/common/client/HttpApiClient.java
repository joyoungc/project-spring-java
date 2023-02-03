package io.joyoungc.api.common.client;

import java.io.IOException;

public interface HttpApiClient {
    String get(String url) throws IOException;
}
