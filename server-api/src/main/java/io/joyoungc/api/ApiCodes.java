package io.joyoungc.api;

import lombok.Getter;

import java.time.Duration;

/***
 * Created by Aiden Jeong on 2021.11.15
 */
public class ApiCodes {

    private static final Duration DURATION_12_HOURS = Duration.ofHours(12);
    private static final Duration DURATION_5_MINUTES = Duration.ofMinutes(5);
    private static final Duration DURATION_10_MINUTES = Duration.ofMinutes(10);
    private static final Duration DURATION_30_MINUTES = Duration.ofMinutes(30);

    public enum Cache {
        cacheUserList(Constants.CACHE_USER_LIST, DURATION_5_MINUTES);

        @Getter
        private final Duration ttl;
        @Getter
        private final String name;

        Cache(String name, Duration ttl) {
            this.name = name;
            this.ttl = ttl;
        }

        public static class Constants {
            public static final String CACHE_USER_LIST = "cacheUserList";
        }
    }
}
