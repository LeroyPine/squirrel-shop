package org.squirrel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author luobaosong
 * @date 2024-06-20 21:38
 */
@Data
@AllArgsConstructor
@Schema(description = "请求登陆实体参数")
public class LoginRequest {


    @Schema(description = "名称")
    private String username;

    @Schema(description = "密码")
    private String password;

}
