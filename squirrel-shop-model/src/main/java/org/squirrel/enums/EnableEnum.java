package org.squirrel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author luobaosong
 * @date 2024-07-22 20:57
 */
@Getter
@AllArgsConstructor
public enum EnableEnum {

    Enable(1, "启用"),
    Disable(0, "禁用");

    private final int code;

    private final String desc;
}
