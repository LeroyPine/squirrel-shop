package org.squirrel.service;

import constant.ErrorCode;
import exception.PasswordNotCorrectException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.squirrel.dto.LoginRequest;
import org.squirrel.dto.UserInfoDto;
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


    public UserInfoDto createToken(LoginRequest loginRequest) {
        AdminUserInfo userInfo = adminUserService.findByName(loginRequest.getUsername());
        if (!userInfo.getUserPwd().equals(loginRequest.getPassword())) {
            throw new PasswordNotCorrectException(ErrorCode.PASSWORD_NOT_VALID, null);
        }
        List<String> roles = userRoleService.findRolesByUserId(userInfo.getUserId());
        // 创建token,并返回
        String token = JwtTokenUtils.generateToken(userInfo.getUserName(), userInfo.getUserId(), roles);
        log.info("userId:{},token:{}", userInfo.getUserId(), token);
        return UserInfoDto.builder()
                .userId(userInfo.getUserId())
                .userName(userInfo.getUserName())
                .roles(roles)
                .token(token)
                .build();

    }

}
