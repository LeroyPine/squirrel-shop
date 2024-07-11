package org.squirrel.filter;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.squirrel.constant.SecurityConstants;
import util.JwtTokenUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Jwt过滤器
 *
 * @author luobaosong
 * @date 2024-06-10 22:49
 */
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final Cache<String, Object> cache;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, Cache<String, Object> userCache) {
        super(authenticationManager);
        this.cache = userCache;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if (token == null || !token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
            return;
        }
        String tokenValue = token.replace(SecurityConstants.TOKEN_PREFIX, Strings.EMPTY);
        UsernamePasswordAuthenticationToken authentication = null;
        try {
            String previousToken = (String) cache.getIfPresent(JwtTokenUtils.getId(tokenValue));
            if (!token.equals(previousToken)) {
                SecurityContextHolder.clearContext();
                chain.doFilter(request, response);
                return;
            }
            authentication = JwtTokenUtils.getAuthentication(tokenValue);
        } catch (Exception e) {
            log.error("JwtAuthorizationFilter error", e);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
