package constant;

/**
 * @author luobaosong
 * @date 2024-06-06 16:14
 */
public class SecurityConstants {


    public static final String JWT_SECRET_KEY = "G7vM4L9hDxJ2kQ5aHjW8NzQ2xL1yK9Pg";

    /**
     * JWT角色的key
     */
    public static final String ROLE_CLAIMS = "rol";

    // JWT token defaults
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";

    // Swagger WHITELIST
    public static final String[] SWAGGER_WHITELIST = {
            "/swagger-ui.html",
            "/swagger-ui/*",
            "/v3/api-docs/**",
    };

    public static final String[] TEST_WHITELIST = {
            "/test/**"
    };

    public static final String[] SYSTEM_WHITELIST = {
            "/auth/login",
            "/users/sign-up"
    };



}
