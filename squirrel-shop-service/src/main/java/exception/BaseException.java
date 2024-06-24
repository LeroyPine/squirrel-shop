package exception;

import constant.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;


@EqualsAndHashCode(callSuper = true)
@Data
abstract class BaseException extends RuntimeException {
    private final ErrorCode errorCode;
    private final transient HashMap<String, Object> data = new HashMap<>();

    BaseException(ErrorCode errorCode, Map<String, Object> data) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        if (!ObjectUtils.isEmpty(data)) {
            this.data.putAll(data);
        }
    }

    BaseException(ErrorCode errorCode, Map<String, Object> data, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
        if (!ObjectUtils.isEmpty(data)) {
            this.data.putAll(data);
        }
    }
}
