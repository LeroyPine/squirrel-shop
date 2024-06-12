package org.squirrel.constant;

/**
 * @author luobaosong
 * @date 2024-06-06 16:14
 */
public class SecurityConstants {

    // Swagger WHITELIST
    public static final String[] SWAGGER_WHITELIST = {
            "/swagger-ui.html",
            "/swagger-ui/*",
            "/v3/api-docs/**",
    };

    public static final String[] TEST_WHITELIST = {
            "/test/**"
    };

}
