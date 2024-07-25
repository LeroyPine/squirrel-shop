package org.squirrel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author luobaosong
 * @date 2024-07-24 13:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserInfoParamDto extends PageDto {

    @Schema(description = "用户名称")
    private String userName;

    @Schema(description = "奖品ID")
    private Integer userId;
}
