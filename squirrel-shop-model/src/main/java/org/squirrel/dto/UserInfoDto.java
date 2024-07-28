package org.squirrel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luobaosong
 * @date 2024-07-23 16:07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {

    @Schema(description = "用户ID")
    private Integer userId;

    @Schema(description = "用户名称")
    private String userName;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "用户电话")
    private String phone;

    @Schema(description = "用户地址")
    private String address;

    @Schema(description = "用户积分")
    private Integer points;
}
