package org.squirrel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.squirrel.biz.MemberPointsBizService;
import org.squirrel.dto.*;
import org.squirrel.service.MemberPointsService;

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
    @Resource
    private MemberPointsService memberPointsService;


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

    @Operation(summary = "会员积分列表", description = "会员积分列表")
    @PostMapping("/list")
    public SquirrelPageDto<MemberPointsListDto> memberPointsList(@RequestBody MemberPointsListParamDto memberPointsListParamDto) {
        return memberPointsService.getMemberPointsList(memberPointsListParamDto);
    }


    @Operation(summary = "会员积分明细", description = "会员积分明细")
    @PostMapping("/historyList")
    public SquirrelPageDto<MemberPointsHistoryListDto> memberPointsHistoryList(@RequestBody MemberPointsListParamDto memberPointsListParamDto) {
        return memberPointsBizService.getMemberPointsHistoryList(memberPointsListParamDto);
    }


}
