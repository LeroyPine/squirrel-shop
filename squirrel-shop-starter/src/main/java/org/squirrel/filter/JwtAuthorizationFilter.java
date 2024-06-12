package org.squirrel.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.squirrel.template.CacheTemplate;

/**
 * @author luobaosong
 * @date 2024-06-10 22:49
 */
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, CacheTemplate cacheTemplate) {
        super(authenticationManager);
    }
}
