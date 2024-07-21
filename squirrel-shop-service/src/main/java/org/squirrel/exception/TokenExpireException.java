package org.squirrel.exception;

import org.squirrel.constant.ErrorCode;

/**
 * @author luobaosong
 * @date 2024-07-02 14:48
 */
public class TokenExpireException extends BizException {

    public TokenExpireException() {
        super(ErrorCode.TOKEN_EXPIRED);
    }
}
