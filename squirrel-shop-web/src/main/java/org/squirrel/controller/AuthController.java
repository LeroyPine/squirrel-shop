package org.squirrel.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.squirrel.constant.Constants;
import org.squirrel.constant.SecurityConstants;
import org.squirrel.dto.AdminUserDetailDto;
import org.squirrel.dto.AdminUserInfoDto;
import org.squirrel.dto.LoginRequest;
import org.squirrel.po.AdminUserInfo;
import org.squirrel.po.UserRole;
import org.squirrel.service.AdminUserService;
import org.squirrel.service.AuthService;
import org.squirrel.service.UserRoleService;
import org.yaml.snakeyaml.scanner.Constant;

import java.util.List;

/**
 * @author luobaosong
 * @date 2024-06-20 21:33
 */
@Slf4j
@Tag(name = "登陆API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AdminUserService adminUserService;
    private final UserRoleService userRoleService;

    @Operation(summary = "登陆", description = "用户登录")
    @PostMapping("/login")
    public ResponseEntity<AdminUserInfoDto> login(@RequestBody LoginRequest loginRequest) {
        AdminUserInfoDto adminUserInfoDto = authService.createToken(loginRequest);
        // 将token添加到响应头
        HttpHeaders headers = new HttpHeaders();
        headers.set(SecurityConstants.TOKEN_HEADER, adminUserInfoDto.getToken());
        headers.set(SecurityConstants.REFRESH_TOKEN, adminUserInfoDto.getRefreshToken());
        log.info("登陆：{}", JSONObject.toJSONString(adminUserInfoDto));
        return new ResponseEntity<>(adminUserInfoDto, headers, HttpStatus.OK);
    }

    @Operation(summary = "刷新token", description = "刷新token")
    @PostMapping("/refreshToken")
    public ResponseEntity<Void> refreshToken(@RequestHeader(value = SecurityConstants.REFRESH_TOKEN) String refreshToken) {
        String token = authService.refreshToken(refreshToken);
        // 将token添加到响应头
        HttpHeaders headers = new HttpHeaders();
        headers.set(SecurityConstants.TOKEN_HEADER, token);
        return new ResponseEntity<>(null, headers, HttpStatus.OK);
    }


    @Operation(summary = "获取管理员信息", description = "获取管理员信息")
    @GetMapping("/getUserDetail")
    public AdminUserDetailDto getUserDetail(@RequestHeader(value = SecurityConstants.TOKEN_HEADER) String authorization) {
        Integer currentUserId = authService.getCurrentUserId();
        AdminUserInfo adminUserInfo = adminUserService.findById(currentUserId);
        List<String> roles = userRoleService.findRolesByUserId(currentUserId);
        return AdminUserDetailDto.builder()
                .userId(currentUserId)
                .userName(adminUserInfo.getUserName())
                .avatar(adminUserInfo.getAvatar())
                .roles(roles)
                .build();
    }


    @Operation(summary = "退出登陆", description = "退出登陆")
    @PostMapping("/logout")
    public void logout(@RequestHeader(value = SecurityConstants.TOKEN_HEADER) String authorization) {
        Integer currentUserId = authService.getCurrentUserId();
        authService.logout(currentUserId);
    }
}
