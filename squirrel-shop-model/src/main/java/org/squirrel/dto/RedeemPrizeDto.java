package org.squirrel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author luobaosong
 * @date 2024-07-24 19:02
 */
@Data
public class RedeemPrizeDto {

    @Schema(description = "用户ID")
    private Integer userId;

    @Schema(description = "奖品ID")
    private String prizeId;

    @Schema(description = "奖品数量")
    private Integer prizeNum;

    @Schema(description = "备注")
    private String remark;


}
