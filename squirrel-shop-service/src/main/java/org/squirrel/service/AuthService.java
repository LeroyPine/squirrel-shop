package org.squirrel.service;

import com.alibaba.fastjson.JSONObject;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.squirrel.constant.ErrorCode;
import org.squirrel.constant.SecurityConstants;
import org.squirrel.dto.LoginRequest;
import org.squirrel.dto.AdminUserInfoDto;
import org.squirrel.dto.UserDetail;
import org.squirrel.exception.BizException;
import org.squirrel.exception.RefreshTokenException;
import org.squirrel.po.AdminUserInfo;
import util.JwtTokenUtils;

import java.util.List;

/**
 * @author luobaosong
 * @date 2024-06-22 13:54
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdminUserService adminUserService;
    private final UserRoleService userRoleService;
    private final Cache<String, Object> cacheTemplate;


    public AdminUserInfoDto createToken(LoginRequest loginRequest) {
        AdminUserInfo userInfo = adminUserService.findByName(loginRequest.getUsername());
        if (!userInfo.getUserPwd().equals(loginRequest.getPassword())) {
            throw new BizException(ErrorCode.PASSWORD_NOT_VALID);
        }
        List<String> roles = userRoleService.findRolesByUserId(userInfo.getUserId());
        // 创建token,并返回
        String token = JwtTokenUtils.generateToken(userInfo.getUserName(), userInfo.getUserId(), roles);
        String refreshToken = JwtTokenUtils.generateRefreshToken(userInfo.getUserName(), userInfo.getUserId(), roles);
        log.info("userId:{},token:{},refreshToken:{}", userInfo.getUserId(), token, refreshToken);
        cacheTemplate.put(String.valueOf(userInfo.getUserId()), token);
        cacheTemplate.put(SecurityConstants.getRefreshTokenKey(userInfo.getUserId()), refreshToken);
        return AdminUserInfoDto.builder()
                .userId(userInfo.getUserId())
                .userName(userInfo.getUserName())
                .roles(roles)
                .token(SecurityConstants.TOKEN_PREFIX + token)
                .refreshToken(refreshToken)
                .build();
    }

    public String refreshToken(String refreshToken) {
        if (StringUtils.isBlank(refreshToken)) {
            throw new RefreshTokenException(ErrorCode.VERIFY_JWT_FAILED);
        }
        String userIdStr = JwtTokenUtils.getRefreshId(refreshToken);
        Integer userId = Integer.parseInt(userIdStr);
        String refreshTokenKey = SecurityConstants.getRefreshTokenKey(userId);
        String refreshTokenFromCache = (String) cacheTemplate.getIfPresent(refreshTokenKey);
        if (!refreshToken.equals(refreshTokenFromCache)) {
            throw new RefreshTokenException(ErrorCode.VERIFY_JWT_FAILED);
        }
        // 重新生成token
        AdminUserInfo userInfo = adminUserService.findById(userId);
        List<String> roles = userRoleService.findRolesByUserId(userId);
        String token = JwtTokenUtils.generateToken(userInfo.getUserName(), userInfo.getUserId(), roles);
        cacheTemplate.put(String.valueOf(userInfo.getUserId()), token);
        return SecurityConstants.TOKEN_PREFIX + token;
    }


    /**
     * 获取当前登陆用户ID
     */
    public Integer getCurrentUserId() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String userDetailStr = JSONObject.toJSONString(authentication.getDetails());
        UserDetail userDetail = JSONObject.parseObject(userDetailStr, UserDetail.class);
        return userDetail.getUserId();
    }

    /**
     * 登出
     */
    public void logout(Integer userId) {
        cacheTemplate.invalidate(userId);
        cacheTemplate.invalidate(SecurityConstants.getRefreshTokenKey(userId));
    }
}
