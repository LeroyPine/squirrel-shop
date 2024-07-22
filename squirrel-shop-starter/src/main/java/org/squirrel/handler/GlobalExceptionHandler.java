package org.squirrel.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.squirrel.exception.BizException;
import org.squirrel.exception.RefreshTokenException;
import org.squirrel.exception.TokenExpireException;
import org.squirrel.vo.ApiResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author luobaosong
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BizException.class)
    public ApiResponse<String> handleBizException(BizException ex, HttpServletRequest request) {
        log.error("handleBizException 请求路径:{},", request.getRequestURI(), ex);
        return ApiResponse.error(ex.getMessage());
    }

    @ExceptionHandler(TokenExpireException.class)
    public ApiResponse<String> handleTokenExpireException(TokenExpireException ex, HttpServletRequest request) {
        log.error("请求路径:{},token已过期", request.getRequestURI(), ex);
        return ApiResponse.errorToken(ex.getErrorCode().getCode(), ex.getMessage());
    }

    @ExceptionHandler(RefreshTokenException.class)
    public ApiResponse<String> handleRefreshTokenException(RefreshTokenException ex, HttpServletRequest request) {
        log.error("请求路径:{},刷新token失败", request.getRequestURI(), ex);
        return ApiResponse.errorToken(ex.getErrorCode().getCode(), ex.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleException(Exception ex, HttpServletRequest request) {
        log.error("请求路径:{},", request.getRequestURI(), ex);
        return ApiResponse.error(ex.getMessage());
    }
}
