package org.squirrel.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author luobaosong
 * @date 2024-06-10 22:49
 */
@Configuration
@EnableCaching
public class CacheConfig {


    /**
     * 用户缓存
     */
    @Bean
    public Cache<String, Object> cacheTemplate() {
        return Caffeine.newBuilder()
                .expireAfterAccess(48, TimeUnit.HOURS)
                .maximumSize(500)
                .build();
    }


}
