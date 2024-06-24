package org.squirrel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.squirrel.dto.LoginRequest;
import org.squirrel.dto.UserInfoDto;
import org.squirrel.service.AuthService;
import org.squirrel.vo.ApiResponse;

/**
 * @author luobaosong
 * @date 2024-06-20 21:33
 */
@Tag(name = "登陆API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "登陆", description = "用户登湖")
    @PostMapping("/login")
    public ResponseEntity<UserInfoDto> login(@RequestBody LoginRequest loginRequest) {
        UserInfoDto userInfoDto = authService.createToken(loginRequest);
        // 将token添加到响应头
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, userInfoDto.getToken());
        return new ResponseEntity<>(userInfoDto, headers, HttpStatus.OK);
    }


}
