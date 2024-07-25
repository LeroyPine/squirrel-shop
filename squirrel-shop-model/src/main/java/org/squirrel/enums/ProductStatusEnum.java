package org.squirrel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author luobaosong
 * @date 2024-07-25 20:12
 */
@Getter
@AllArgsConstructor
public enum ProductStatusEnum {

    VALID_STATUS(1, "有效"),
    INVALID_STATUS(1, "无效"),
    ;

    private final int code;

    private final String desc;

}
