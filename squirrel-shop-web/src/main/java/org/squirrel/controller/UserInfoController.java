package org.squirrel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.squirrel.biz.UserInfoBizService;
import org.squirrel.dto.*;
import org.squirrel.service.UserInfoService;

import javax.annotation.Resource;

/**
 * @author luobaosong
 * @date 2024-06-02 22:08
 */
@RestController()
@RequestMapping("/user")
@Tag(name = "用户信息API")
public class UserInfoController {

    @Resource
    private UserInfoBizService userInfoBizService;
    @Resource
    private UserInfoService userInfoService;

    @Operation(summary = "保存用户信息", description = "保存用户信息")
    @PostMapping("/saveUserInfo")
    public void saveUserInfo(@RequestParam UserInfoDto userInfoDto) {
        userInfoBizService.saveUserInfo(userInfoDto);
    }


    @Operation(summary = "修改用户信息", description = "修改用户信息")
    @PostMapping("/updateUserInfo")
    public void updateUserInfo(@RequestParam UserInfoDto userInfoDto) {
        userInfoBizService.updateUserInfo(userInfoDto);
    }


    @Operation(summary = "获取所有用户信息", description = "获取所有用户信息")
    @PostMapping("/getAllUser")
    public SquirrelPageDto<UserInfoDto> getAllUserInfo(@RequestBody UserInfoParamDto userInfoParamDto) {
        return userInfoService.getAllUserInfo(userInfoParamDto);
    }


    @Operation(summary = "获取用户信息", description = "获取用户信息")
    @PostMapping("/getUserById")
    public UserInfoDto getUserInfo(Integer userId) {
        return userInfoService.findUserInfoByUserId(userId);
    }


}
