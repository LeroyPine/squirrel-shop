package org.squirrel.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author luobaosong
 */

@Getter
public enum ErrorCode {
    USER_NAME_NOT_FOUND(1002, "未找到指定用户"),
    VERIFY_JWT_FAILED(1003, "刷新token验证失败"),
    METHOD_ARGUMENT_NOT_VALID(1003, "方法参数验证失败"),
    PASSWORD_NOT_VALID(1004, "请输入正确的密码!"),
    TOKEN_EXPIRED(1005, "token已过期"),
    INSUFFICIENT_PERMISSIONS(1006, "权限不够"),
    POINT_NOT_ENOUGH(1007, "用户所剩积分不足以兑换该奖品!"),
    BUY_DETAIL_DUP(1008, "消费明细不能存在提交多个重复的商品,可进行单个商品数量的变更!"),

    ;
    private final int code;


    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
