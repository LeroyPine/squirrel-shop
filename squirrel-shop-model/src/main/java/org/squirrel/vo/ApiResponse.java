package org.squirrel.vo;

import lombok.Data;

/**
 * @author luobaosong
 * @date 2024-06-22 12:40
 */
@Data
public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;

    // Constructors
    public ApiResponse() {
    }

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public static ApiResponse<String> error(String message) {
        return new ApiResponse<>(-1, message, null);
    }

    public static ApiResponse<String> errorToken(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }
}
