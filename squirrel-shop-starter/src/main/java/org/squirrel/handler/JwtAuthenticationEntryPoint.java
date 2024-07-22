package org.squirrel.handler;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.squirrel.constant.ErrorCode;
import org.squirrel.exception.TokenExpireException;
import org.squirrel.vo.ApiResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author luobaosong
 * @date 2024-06-16 17:45
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 当用户尝试访问需要权限才能的REST资源而不提供Token或者Token错误或者过期时，
     * 将调用此方法发送401响应以及错误信息
     */

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        ApiResponse<String> stringApiResponse = ApiResponse.errorToken(ErrorCode.TOKEN_EXPIRED.getCode(), ErrorCode.TOKEN_EXPIRED.getMessage());
        httpServletResponse.getWriter().write(JSONObject.toJSONString(stringApiResponse));
    }
}
