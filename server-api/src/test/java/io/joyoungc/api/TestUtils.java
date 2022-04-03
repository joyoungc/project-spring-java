package io.joyoungc.api;

/***
 * Created by Aiden Jeong on 2022.04.03
 */
public class TestUtils {

    public static String createUrlWithPort(String uri, int port) {
        return String.format("http://localhost:%s%s", port, uri);
    }
}
