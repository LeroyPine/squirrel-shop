package org.squirrel.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.squirrel.service.AdminUserService;

import javax.annotation.Resource;

/**
 * @author luobaosong
 * @date 2024-06-02 22:08
 */
@RestController("/user")
public class UserController {

    @Resource
    private AdminUserService adminUserService;

    @RequestMapping("/findAll")
    public Object findAll() {
        return adminUserService.findAll();
    }

}
