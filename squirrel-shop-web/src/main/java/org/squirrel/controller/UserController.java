package org.squirrel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.squirrel.service.AdminUserService;

import javax.annotation.Resource;

/**
 * @author luobaosong
 * @date 2024-06-02 22:08
 */
@RestController()
@RequestMapping("/user")
@Tag(name = "用户信息API")
public class UserController {

    @Resource
    private AdminUserService adminUserService;

    @Operation(summary = "获取用户信息", description = "获取指定用户信息")
    @GetMapping("/findById")
    public Object findById(@RequestParam Integer userId) {
        return adminUserService.findById(userId);
    }


}
