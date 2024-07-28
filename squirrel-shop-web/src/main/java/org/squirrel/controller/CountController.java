package org.squirrel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.squirrel.dto.BuyProductDto;
import org.squirrel.dto.UserRankDto;
import org.squirrel.dto.WeeklyMoneyDto;
import org.squirrel.service.CountService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author luobaosong
 * @date 2024-07-28 18:18
 */
@Slf4j
@RestController
@RequestMapping("/count")
@Tag(name = "统计管理")
public class CountController {

    @Resource
    private CountService countService;

    @Operation(summary = "积分统计", description = "积分统计")
    @GetMapping("/point")
    public Integer countPointByType(@RequestParam(value = "type") Integer type) {
        return countService.countPointByType(type);
    }

    @Operation(summary = "金额统计", description = "金额统计")
    @GetMapping("/money")
    public BigDecimal sumMoneyByType(@RequestParam(value = "type") Integer type) {
        return countService.sumMoneyByType(type);
    }


    @Operation(summary = "用户统计", description = "用户统计")
    @GetMapping("/user")
    public Integer countUserByType(@RequestParam(value = "type") Integer type) {
        return countService.countUserByType(type);
    }

    @Operation(summary = "周金额统计", description = "周金额统计")
    @GetMapping("/weekMoney")
    public List<WeeklyMoneyDto> countWeeklyMoney() {
        return countService.countWeeklyMoney();
    }

    @Operation(summary = "用户排行榜", description = "用户排行榜")
    @GetMapping("/userRank")
    public List<UserRankDto> getUserRank() {
        return countService.getUserRank();
    }


}
