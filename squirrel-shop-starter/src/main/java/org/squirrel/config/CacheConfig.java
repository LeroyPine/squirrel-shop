package org.squirrel.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author luobaosong
 * @date 2024-06-10 22:49
 */
@Slf4j
@Component
public class CacheConfig {

    public static Map<Object, Object> cacheMap = new HashMap<>();

}
