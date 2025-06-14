package org.squirrel.handler;

import com.alibaba.fastjson.JSONObject;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.squirrel.constant.ErrorCode;
import org.squirrel.exception.BizException;
import org.squirrel.vo.ApiResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author luobaosong
 * @date 2024-06-16 17:45
 */
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 当用户尝试访问需要权限才能的REST资源而权限不足的时候，
     * 将调用此方法发送403响应以及错误信息
     */
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        ApiResponse<String> stringApiResponse = ApiResponse.errorToken(ErrorCode.VERIFY_JWT_FAILED.getCode(), ErrorCode.VERIFY_JWT_FAILED.getMessage());
        httpServletResponse.getWriter().write(JSONObject.toJSONString(stringApiResponse));
    }
}
