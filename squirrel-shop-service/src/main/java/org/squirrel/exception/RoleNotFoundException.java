package org.squirrel.exception;

import org.squirrel.constant.ErrorCode;

import java.util.Map;

/**
 * @author shuang.kou
 */
public class RoleNotFoundException extends BaseException {
    public RoleNotFoundException(Map<String, Object> data) {
        super(ErrorCode.ROLE_NOT_FOUND, data);
    }
}
