package org.squirrel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author luobaosong
 * @date 2024-07-27 15:07
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/squirrel-shop/files/**")
                .addResourceLocations("file:/Users/luobaosong/file/squirrel-shop/");
    }
}
