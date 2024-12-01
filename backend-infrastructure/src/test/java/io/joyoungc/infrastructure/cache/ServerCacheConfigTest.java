package io.joyoungc.infrastructure.cache;

import io.joyoungc.infrastructure.constant.Profiles;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(classes = {ServerCacheConfig.class, ServerCacheConfigTest.DummyCache.class,
        CacheAutoConfiguration.class}, initializers = ConfigDataApplicationContextInitializer.class)
@TestPropertySource(properties = {
        "spring.cache.type=simple",
        "cache.default-expire-minute=3",
        "cache.cache-map-enabled=true",
        "cache.cache-map.cache-name1=5",
        "cache.cache-map.cache-name2=2"
})
@ActiveProfiles({Profiles.CACHE, Profiles.TEST})
class ServerCacheConfigTest {

    @Autowired
    CacheManager cacheManager;

    @Autowired
    DummyCache dummyCache;

    @Test
    void check_cacheNames() {
        // when
        Collection<String> cacheNames = cacheManager.getCacheNames();
        // then
        assertThat(cacheNames).contains(DummyCache.TEST_CACHE1, DummyCache.TEST_CACHE2);
    }

    @Test
    void test_cacheable() {
        // given
        // let's make a cache
        DummyObject result1 = dummyCache.cacheable("test1");
        assertThat(result1).isNotNull();
        assertThat(result1.getName()).isEqualTo("name");

        // then
        // retrieve from cache
        DummyObject result2 = dummyCache.cacheable("test1");
        assertThat(result2).isNotNull();
        assertThat(result2.getName()).isEqualTo("name");

        Cache cache = cacheManager.getCache(DummyCache.TEST_CACHE1);
        assertThat(cache).isNotNull();
        Object object = cache.get("test1").get();
        assertThat(object).isNotNull().isInstanceOf(DummyObject.class);
        DummyObject test1 = (DummyObject) object;
        assertThat(test1.getName()).isEqualTo("name");
    }

    @Test
    void test_evict() {
        // given
        // let's make a cache
        DummyObject result1 = dummyCache.cacheable("test1");
        assertThat(result1).isNotNull();
        assertThat(result1.getName()).isEqualTo("name");

        // when
        dummyCache.evict("test1");

        // then
        Cache cache = cacheManager.getCache(DummyCache.TEST_CACHE1);
        assertThat(cache).isNotNull();
        Cache.ValueWrapper wrapper = cache.get("test1");
        assertThat(wrapper).isNull();
    }


    public static class DummyCache {
        public static final String TEST_CACHE1 = "cache-name1";
        public static final String TEST_CACHE2 = "cache-name2";

        @Cacheable(value = TEST_CACHE1)
        public DummyObject cacheable(String key) {
            System.out.println("## in cacheable method : " + key);
            DummyObject dummyObject = new DummyObject();
            dummyObject.setName("name");
            dummyObject.setCreatedAt(LocalDateTime.now());
            dummyObject.setList(List.of("one", "two", "three"));
            dummyObject.setMap(Map.of("string", "value", "int", 100));
            return dummyObject;
        }

        @CacheEvict(value = TEST_CACHE1)
        public void evict(String key) {
            System.out.println("## in evict method : " + key);
        }

    }

    @Data
    public static class DummyObject {
        private String name;
        private LocalDateTime createdAt;
        private List<String> list;
        private Map<String, Object> map;
    }

}