package org.squirrel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.squirrel.po.MemberPointsConfig;
import org.squirrel.service.MemberPointsConfigService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author luobaosong
 * @date 2024-07-23 18:00
 */
@RestController
@RequestMapping("/memberPointConfig")
@Tag(name = "会员积分配置")
public class MemberPointsConfigController {

    @Resource
    private MemberPointsConfigService memberPointsConfigService;

    @Operation(summary = "获取积分配置信息", description = "获取积分配置信息")
    @GetMapping("/getAllMemberPointsConfig")
    public List<MemberPointsConfig> getMemberPointsConfigList() {
        return memberPointsConfigService.findAllMemberPointsConfig();
    }

}
