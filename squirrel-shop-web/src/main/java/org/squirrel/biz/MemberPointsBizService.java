package org.squirrel.biz;

import com.alibaba.fastjson.JSONObject;
import com.github.f4b6a3.uuid.UuidCreator;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.squirrel.constant.ErrorCode;
import org.squirrel.dto.*;
import org.squirrel.enums.BuyDetailTypeEnum;
import org.squirrel.enums.MemberChangeTypeEnum;
import org.squirrel.enums.MemberPointsConfigTypeEnum;
import org.squirrel.exception.BizException;
import org.squirrel.po.*;
import org.squirrel.service.*;
import util.DateUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author luobaosong
 * @date 2024-07-24 18:51
 */
@Slf4j
@Service
public class MemberPointsBizService {

    @Resource
    private MemberPointsService memberPointsService;
    @Resource
    private MemberPointsHistoryService memberPointsHistoryService;
    @Resource
    private MemberPointsHistoryDetailService memberPointsHistoryDetailService;
    @Resource
    private PrizeService prizeService;
    @Resource
    private MemberPointsBizService memberPointsBizService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private MemberPointsConfigService memberPointsConfigService;
    @Resource
    private ProductService productService;


    /**
     * 兑换奖品
     *
     * @param redeemPrizeDto 奖品
     */
    public void redeemPrizes(RedeemPrizeDto redeemPrizeDto) {
        Integer userId = redeemPrizeDto.getUserId();
        String prizeId = redeemPrizeDto.getPrizeId();
        Integer prizeNum = redeemPrizeDto.getPrizeNum();

        // 获取当前用户积分
        UserInfoDto userInfoDto = userInfoService.findUserInfoByUserId(userId);
        if (Objects.isNull(userInfoDto) || Objects.isNull(userInfoDto.getPoints())) {
            throw new IllegalArgumentException("当前用户积分为空!");
        }
        Integer points = userInfoDto.getPoints();

        // 获取兑换奖品所需积分
        PrizeDto prizeDto = prizeService.findByPrizeId(prizeId);
        if (Objects.isNull(prizeDto)) {
            throw new IllegalArgumentException("奖品信息为空!");
        }
        int consumePoints = prizeDto.getPoints() * prizeNum;
        if (consumePoints > points) {
            throw new BizException(ErrorCode.POINT_NOT_ENOUGH);
        }
        // 兑换奖品
        memberPointsBizService.doRedeemPrizes(userInfoDto, prizeDto, redeemPrizeDto, consumePoints);
    }

    @Transactional(rollbackFor = Exception.class)
    public void doRedeemPrizes(UserInfoDto userInfoDto, PrizeDto prizeDto, RedeemPrizeDto redeemPrizeDto, int consumePoints) {
        // 减少产品数量
        int prizeUpdateRow = prizeService.reducePrizeNum(prizeDto.getPrizeId(), redeemPrizeDto.getPrizeNum());
        if (prizeUpdateRow <= 0) {
            throw new IllegalArgumentException("当前奖品库存数量不足,请增加奖品数量!");
        }

        // 修改积分
        int memberPointsUpdateRow = memberPointsService.reduceMemberPoints(userInfoDto.getUserId(), consumePoints);
        if (memberPointsUpdateRow <= 0) {
            throw new IllegalArgumentException("当前用户积分不足,无法兑换奖品!");
        }

        String userName = userInfoDto.getUserName();
        Integer beforePoints = userInfoDto.getPoints();
        int afterPoints = userInfoDto.getPoints() - consumePoints;

        // 记录积分变更历史
        String changeDesc = String.format("用户%s,兑换奖品:%s,数量:%s,共消耗%s积分,剩余积分为:%s,时间:%s",
                userName, prizeDto.getPrizeName(), redeemPrizeDto.getPrizeNum(), consumePoints, afterPoints,
                DateUtils.getCurrentDateTime());
        MemberPointsHistory pointsHistory = MemberPointsHistory.builder()
                .changeType(MemberChangeTypeEnum.REDEEMED_POINTS.getCode())
                .redeemedPoints(consumePoints)
                .beforePoints(beforePoints)
                .afterPoints(afterPoints)
                .changeDesc(changeDesc)
                .remark(redeemPrizeDto.getRemark())
                .userId(userInfoDto.getUserId())
                .build();
        Integer pointsHistoryId = memberPointsHistoryService.saveMemberPointsHistory(pointsHistory);

        // 记录明细
        MemberPointsHistoryDetail memberPointsHistoryDetail = MemberPointsHistoryDetail.builder()
                .historyId(pointsHistoryId)
                .productName(prizeDto.getPrizeName())
                .productId(prizeDto.getPrizeId())
                .productPoints(prizeDto.getPoints())
                .productNum(redeemPrizeDto.getPrizeNum())
                .build();
        memberPointsHistoryDetailService.saveMemberPointsHistoryDetail(memberPointsHistoryDetail);
    }


    public void buyProduct(BuyProductDto buyProductDto) {
        Integer userId = buyProductDto.getUserId();
        BigDecimal price = buyProductDto.getPrice();
        // 获取金额对应的积分配置
        MemberPointsConfigTypeEnum configTypeEnum = MemberChangeTypeEnum.MEMBER_CHANGE_TYPE_POINTS_CONFIG_MAP.get(MemberChangeTypeEnum.BUY_PRODUCT.getCode());
        MemberPointsConfig pointsConfig = memberPointsConfigService.findMemberPointsConfigByType(configTypeEnum.getCode());
        if (Objects.isNull(pointsConfig)) {
            throw new IllegalArgumentException("积分金额配置为空,请检查配置是否正确!");
        }
        // 1积分对应的金额
        String configRule = pointsConfig.getConfigRule();
        BuyProductConfigRule buyProductConfigRule = JSONObject.parseObject(configRule, BuyProductConfigRule.class);
        BigDecimal money = buyProductConfigRule.getMoney();
        // 计算购买商品所得积分,积分不够直接取整
        if (price == null || money == null || money.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("金额积分不合法!");
        }
        Integer points = price.divideToIntegralValue(money).intValue();
        UserInfoDto userInfoDto = userInfoService.findUserInfoByUserId(userId);
        if (Objects.isNull(userInfoDto) || Objects.isNull(userInfoDto.getPoints())) {
            throw new IllegalArgumentException("当前用户积分为空!");
        }
        memberPointsBizService.doBuyProduct(userInfoDto, buyProductDto, points);
    }

    @Transactional(rollbackFor = Exception.class)
    public void doBuyProduct(UserInfoDto userInfoDto, BuyProductDto buyProductDto, Integer points) {

        Integer beforePoint = userInfoDto.getPoints();
        Integer afterPoint = beforePoint + points;
        String userName = userInfoDto.getUserName();

        // 修改会员积分
        MemberPoints updateMemberPoints = new MemberPoints();
        updateMemberPoints.setUserId(userInfoDto.getUserId());
        updateMemberPoints.setPoints(afterPoint);
        memberPointsService.updateMemberPoints(updateMemberPoints);

        String changeDesc = String.format("用户%s,消费金额:%s,得到%s积分,剩余积分为:%s,时间:%s",
                userName, buyProductDto.getPrice(), points, afterPoint, DateUtils.getCurrentDateTime());
        // 记录消费记录
        MemberPointsHistory pointsHistory = MemberPointsHistory.builder()
                .changeType(MemberChangeTypeEnum.BUY_PRODUCT.getCode())
                .amount(buyProductDto.getPrice())
                .beforePoints(beforePoint)
                .afterPoints(afterPoint)
                .changeDesc(changeDesc)
                .remark(buyProductDto.getRemark())
                .userId(userInfoDto.getUserId())
                .build();
        memberPointsHistoryService.saveMemberPointsHistory(pointsHistory);
    }

    @Transactional(rollbackFor = Exception.class)
    public void buyProductDetail(BuyProductDetailDto buyProductDetailDto) {
        List<BuyProductDetailDto.ProductDetailDto> productDetailDtoList = buyProductDetailDto.getProductDetailDtoList();
        if (CollectionUtils.isEmpty(productDetailDtoList)) {
            return;
        }
        Integer historyId = buyProductDetailDto.getHistoryId();

        // 校验明细金额总计是否与该次消费金额一致
        MemberPointsHistory pointsHistory = memberPointsHistoryService.findMemberPointsHistoryById(historyId);
        BigDecimal amount = pointsHistory.getAmount();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (BuyProductDetailDto.ProductDetailDto productDetailDto : productDetailDtoList) {
            totalAmount = totalAmount.add(BigDecimal.valueOf(productDetailDto.getProductNum()).multiply(productDetailDto.getPrice()));
        }
        if (amount.compareTo(totalAmount) != 0) {
            throw new IllegalArgumentException("录入的明细金额与客户此次购物消费金额不同,请修改!");
        }

        // 记录消费明细
        try {
            productDetailDtoList.forEach(productDetailDto -> {
                MemberPointsHistoryDetail memberPointsHistoryDetail = MemberPointsHistoryDetail.builder()
                        .historyId(buyProductDetailDto.getHistoryId())
                        .productName(productDetailDto.getProductName())
                        .productId(productDetailDto.getProductId())
                        .productMoney(productDetailDto.getPrice())
                        .productNum(productDetailDto.getProductNum())
                        .build();
                memberPointsHistoryDetailService.saveMemberPointsHistoryDetail(memberPointsHistoryDetail);
            });
        } catch (DuplicateKeyException e) {
            throw new BizException(ErrorCode.BUY_DETAIL_DUP);
        }

        // 如果非选择商品录入情况下,直接将该批商品保存至商品库
        Integer buyDetailType = buyProductDetailDto.getBuyDetailType();
        BuyDetailTypeEnum detailTypeEnum = BuyDetailTypeEnum.getType(buyDetailType);
        if (BuyDetailTypeEnum.CHOOSE_PRODUCT.equals(detailTypeEnum)) {
            return;
        }
        List<Product> productList = Lists.newArrayList();
        productDetailDtoList.forEach(productDetailDto -> {
            productList.add(Product.builder()
                    .productName(productDetailDto.getProductName())
                    .productMoney(productDetailDto.getPrice())
                    .build());
        });
        Map<String, String> productyNameIdMap = productService.saveProductByWithOutId(productList);
        // 更新该笔消费下新录入的商品ID
        List<MemberPointsHistoryDetail> memberPointsHistoryDetailList = memberPointsHistoryDetailService.findMemberPointsHistoryDetailByHistoryId(historyId);
        if (CollectionUtils.isEmpty(memberPointsHistoryDetailList)) {
            return;
        }
        try {
            memberPointsHistoryDetailList.forEach(memberPointsHistoryDetail -> {
                String productId = productyNameIdMap.get(memberPointsHistoryDetail.getProductName());
                if (StringUtils.isBlank(productId)) {
                    return;
                }
                if (StringUtils.isNotBlank(memberPointsHistoryDetail.getProductId())) {
                    return;
                }
                memberPointsHistoryDetail.setProductId(productId);
                memberPointsHistoryDetailService.updateMemberPointsHistoryDetail(memberPointsHistoryDetail);
            });
        } catch (DuplicateKeyException e) {
            throw new BizException(ErrorCode.BUY_DETAIL_DUP);
        }
    }
}
