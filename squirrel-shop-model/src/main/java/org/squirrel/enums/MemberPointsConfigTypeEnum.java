package org.squirrel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author luobaosong
 * @date 2024-07-23 19:31
 */
@Getter
@AllArgsConstructor
public enum MemberPointsConfigTypeEnum {

    /**
     * 默认规则,购买商品
     */
    BUY_PRODUCT_RULE(1);

    private final int code;

}
