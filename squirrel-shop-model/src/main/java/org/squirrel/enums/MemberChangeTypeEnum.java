package org.squirrel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luobaosong
 * @date 2024-07-23 19:28
 */
@Getter
@AllArgsConstructor
public enum MemberChangeTypeEnum {

    UPDATE_BASE_INFO(1, "用户信息修改"),
    BUY_PRODUCT(2, "购买商品"),
    REDEEMED_POINTS(3, "兑换积分"),

    ;

    private final int code;
    private final String desc;


    public static final Map<Integer, MemberPointsConfigTypeEnum> MEMBER_CHANGE_TYPE_POINTS_CONFIG_MAP = new HashMap<>();

    static {
        MEMBER_CHANGE_TYPE_POINTS_CONFIG_MAP.put(BUY_PRODUCT.getCode(), MemberPointsConfigTypeEnum.BUY_PRODUCT_RULE);
    }

}
