package org.squirrel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author luobaosong
 * @date 2024-07-25 17:52
 */
@Getter
@AllArgsConstructor
public enum BuyDetailTypeEnum {

    CHOOSE_PRODUCT(1, "选择商品信息"),
    ENTER_PRODUCT(2, "手动录入商品信息");


    private final int code;
    private final String desc;


    public static BuyDetailTypeEnum getType(Integer code) {
        return Arrays.stream(values()).filter(e -> e.code == code).findFirst().orElseThrow(() -> new IllegalArgumentException("获取不到补充商品信息类型!"));
    }
}
