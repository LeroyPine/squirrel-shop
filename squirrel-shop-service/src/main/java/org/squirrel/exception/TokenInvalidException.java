package org.squirrel.exception;

import org.squirrel.constant.ErrorCode;

import java.util.Map;

/**
 * @author luobaosong
 * @date 2024-07-02 14:48
 */
public class TokenInvalidException extends BaseException{

    public TokenInvalidException(ErrorCode errorCode, Map<String, Object> data) {
        super(errorCode, data);
    }
}
