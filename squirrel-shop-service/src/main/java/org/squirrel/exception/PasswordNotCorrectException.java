package org.squirrel.exception;

import org.squirrel.constant.ErrorCode;

import java.util.Map;

/**
 * @author luobaosong
 * @date 2024-06-22 15:27
 */
public class PasswordNotCorrectException extends BaseException {
    public PasswordNotCorrectException(ErrorCode errorCode, Map<String, Object> data) {
        super(ErrorCode.METHOD_ARGUMENT_NOT_VALID, data);
    }
}
