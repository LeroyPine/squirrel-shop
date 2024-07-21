package org.squirrel.exception;

import org.squirrel.constant.ErrorCode;

import java.util.Map;

/**
 * @author luobaosong
 * @date 2024-06-22 14:52
 */
public class UserNameNotFoundException extends BizException {

    public UserNameNotFoundException() {
        super(ErrorCode.USER_NAME_NOT_FOUND);
    }
}
