package org.squirrel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.squirrel.constant.SecurityConstants;
import org.squirrel.dto.LoginRequest;
import org.squirrel.dto.UserInfoDto;
import org.squirrel.service.AuthService;

/**
 * @author luobaosong
 * @date 2024-06-20 21:33
 */
@Tag(name = "登陆API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    @Operation(summary = "登陆", description = "用户登录")
    @PostMapping("/login")
    public ResponseEntity<UserInfoDto> login(@RequestBody LoginRequest loginRequest) {
        UserInfoDto userInfoDto = authService.createToken(loginRequest);
        // 将token添加到响应头
        HttpHeaders headers = new HttpHeaders();
        headers.set(SecurityConstants.TOKEN_HEADER, userInfoDto.getToken());
        headers.set(SecurityConstants.REFRESH_TOKEN, userInfoDto.getRefreshToken());
        return new ResponseEntity<>(userInfoDto, headers, HttpStatus.OK);
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


    @Operation(summary = "testJwt", description = "testJwt")
    @PostMapping("/testJwt")
    public String testJwt(@RequestHeader(value = SecurityConstants.TOKEN_HEADER) String Authorization) {
        log.info("testJwt");
        return "jwt";
    }

}
