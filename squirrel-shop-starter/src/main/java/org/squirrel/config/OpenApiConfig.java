package org.squirrel.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.squirrel.constant.SecurityConstants;

import java.util.Collection;
import java.util.List;

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
                .components(new Components()
                        .addSecuritySchemes(SecurityConstants.TOKEN_HEADER, new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name(SecurityConstants.TOKEN_HEADER)));
    }

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("login")
                .pathsToMatch("/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi webApi() {
        return GroupedOpenApi.builder()
                .group("webApi")
                .pathsToExclude("/auth/**")
                .addOpenApiCustomiser(customerHeaderOpenApiCustomiser())
                .build();
    }


    @Bean
    public OpenApiCustomiser customerHeaderOpenApiCustomiser() {
        return new OpenApiCustomiser() {
            @Override
            public void customise(OpenAPI openApi) {
                Paths paths = openApi.getPaths();
                Collection<PathItem> values = paths.values();
                for (PathItem pathItem : values) {
                    List<Operation> operations = pathItem.readOperations();
                    for (Operation operation : operations) {
                        HeaderParameter headerParameter = new HeaderParameter();
                        headerParameter.setName(SecurityConstants.TOKEN_HEADER);
                        operation.addParametersItem(headerParameter);
                    }
                }
            }
        };
    }

}
