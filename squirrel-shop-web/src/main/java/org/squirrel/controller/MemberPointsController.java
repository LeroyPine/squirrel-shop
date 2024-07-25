package org.squirrel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.squirrel.biz.MemberPointsBizService;
import org.squirrel.dto.BuyProductDetailDto;
import org.squirrel.dto.BuyProductDto;
import org.squirrel.dto.RedeemPrizeDto;

import javax.annotation.Resource;

/**
 * @author luobaosong
 * @date 2024-07-24 18:50
 */
@RestController
@RequestMapping("/memberPoints")
@Tag(name = "积分管理")
public class MemberPointsController {

    @Resource
    private MemberPointsBizService memberPointsBizService;


    @Operation(summary = "兑换奖品", description = "兑换奖品")
    @PostMapping("/redeemPrizes")
    public void redeemPrizes(@RequestBody RedeemPrizeDto redeemPrizeDto) {
        memberPointsBizService.redeemPrizes(redeemPrizeDto);
    }


    @Operation(summary = "购买商品消耗积分", description = "购买商品消耗积分")
    @PostMapping("/buyProduct")
    public void buyProduct(@RequestBody BuyProductDto buyProductDto) {
        memberPointsBizService.buyProduct(buyProductDto);
    }


    @Operation(summary = "购买商品明细", description = "购买商品明细")
    @PostMapping("/buyProductDetail")
    public void buyProductDetail(@RequestBody BuyProductDetailDto buyProductDetailDto) {
        memberPointsBizService.buyProductDetail(buyProductDetailDto);
    }


}
