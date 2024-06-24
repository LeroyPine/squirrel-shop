package org.squirrel.vo;

import lombok.Data;

/**
 * @author luobaosong
 * @date 2024-06-22 12:40
 */
@Data
public class ApiResponse<T> {

    private int status;
    private String message;
    private T data;

    // Constructors
    public ApiResponse() {
    }

    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
