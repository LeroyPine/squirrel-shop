package org.squirrel.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author luobaosong
 * @date 2024-07-27 15:07
 */
@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 获取操作系统
        String os = System.getProperty("os.name").toLowerCase();
        log.info("WebConfig 当前操作系统:{}", os);
        if (os.contains("win")) {
            registry.addResourceHandler("/squirrel-shop/files/**")
                    .addResourceLocations("file:C:/file/squirrel-shop/");
        } else if (os.contains("mac")) {
            registry.addResourceHandler("/squirrel-shop/files/**")
                    .addResourceLocations("file:/Users/luobaosong/file/squirrel-shop/");
        }
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("*");


    }
}
