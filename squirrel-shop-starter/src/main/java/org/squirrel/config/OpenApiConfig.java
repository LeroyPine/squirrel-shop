package org.squirrel.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luobaosong
 * @date 2024-06-06 10:14
 */
@Configuration
@Slf4j
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Squirrel-Shop API")
                        .version("1.0")
                        .description("Squirrel-Shop API 文档"))
                .components(
                        new Components()
                                .addParameters("Authorization", new Parameter()
                                        .in("header")
                                        .required(false)
                                        .schema(new io.swagger.v3.oas.models.media.StringSchema())
                                        .name("Authorization")
                                        .description("JWT Token"))
                );
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .build();
    }


}
