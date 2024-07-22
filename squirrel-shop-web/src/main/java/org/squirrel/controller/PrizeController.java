package org.squirrel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.squirrel.biz.PrizeBizService;
import org.squirrel.dto.PrizeDto;
import org.squirrel.dto.PrizeParamDto;
import org.squirrel.dto.SquirrelPageDto;
import org.squirrel.service.PrizeService;

import javax.annotation.Resource;

/**
 * @author luobaosong
 * @date 2024-07-21 22:55
 */
@RestController()
@RequestMapping("/prize")
@Tag(name = "奖品信息")
public class PrizeController {

    @Resource
    private PrizeService prizeService;
    @Resource
    private PrizeBizService prizeBizService;


    @Operation(summary = "保存奖品信息", description = "保存奖品信息")
    @PostMapping("/savePrize")
    public void savePrize(@RequestBody PrizeDto prizeDto) {
        prizeBizService.savePrize(prizeDto);
    }

    @Operation(summary = "修改奖品信息", description = "修改奖品信息")
    @PostMapping("/updatePrize")
    public void updatePrize(@RequestBody PrizeDto prizeDto) {
        prizeService.updatePrizeById(prizeDto);
        prizeBizService.updatePrizeById(prizeDto);
    }


    @Operation(summary = "获取所有奖品信息", description = "获取奖品信息")
    @PostMapping("/getAllPrize")
    public SquirrelPageDto<PrizeDto> getAllPrize(@RequestBody PrizeParamDto prizeParamDto) {
        return prizeService.findAllPrizes(prizeParamDto);
    }


    @Operation(summary = "获取指定奖品信息", description = "获取指定奖品信息")
    @PostMapping("/getPrizeById")
    public PrizeDto getPrizeById(@RequestParam(value = "prizeId") String prizeId) {
        return prizeService.findByPrizeId(prizeId);
    }

}
