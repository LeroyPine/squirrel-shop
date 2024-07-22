package org.squirrel.exception;

import org.squirrel.constant.ErrorCode;

/**
 * @author luobaosong
 * @date 2024-07-02 14:48
 */
public class RefreshTokenException extends BizException {

    public RefreshTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
