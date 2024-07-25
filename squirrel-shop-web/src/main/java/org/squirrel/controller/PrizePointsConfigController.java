package org.squirrel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.squirrel.dto.PrizePointsConfigDto;
import org.squirrel.service.PrizePointsConfigService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author luobaosong
 * @date 2024-07-22 21:03
 */
@RestController
@RequestMapping("/prizePointConfig")
@Tag(name = "奖品积分配置")
public class PrizePointsConfigController {


    @Resource
    private PrizePointsConfigService prizePointsConfigService;


    @Operation(summary = "获取奖品积分配置信息", description = "获取奖品积分配置信息")
    @PostMapping("/getAllPrizePointsConfig")
    public List<PrizePointsConfigDto> getAllPrizePointsConfig() {
        return prizePointsConfigService.findAllPrizePointsConfig();
    }
}
