package org.squirrel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.squirrel.constant.SecurityConstants;
import org.squirrel.filter.JwtAuthorizationFilter;
import org.squirrel.template.CacheTemplate;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author luobaosong
 * @date 2024-06-06 15:34
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final CacheTemplate cacheTemplate;

    public SecurityConfig(CacheTemplate cacheTemplate) {
        this.cacheTemplate = cacheTemplate;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors(withDefaults())
                // 禁用 CSRF
                .csrf()
                .disable()
                .authorizeRequests()
                // 指定的接口直接放行
                .antMatchers(SecurityConstants.SWAGGER_WHITELIST).permitAll()
                .antMatchers(SecurityConstants.TEST_WHITELIST).permitAll()
                //.antMatchers(HttpMethod.POST, SecurityConstants.SYSTEM_WHITELIST).permitAll()
                // 其他的接口都需要认证后才能请求
                .anyRequest()
                .authenticated()
                .and()
                //添加自定义Filter
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), cacheTemplate))
                // 不需要session（不创建会话）
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();
        // 授权异常处理
        //  .exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint())
        //  .accessDeniedHandler(new JwtAccessDeniedHandler());
        // 防止H2 web 页面的Frame 被拦截
        //http.headers().frameOptions().disable();
    }
}
