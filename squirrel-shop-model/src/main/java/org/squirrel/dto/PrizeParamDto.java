package org.squirrel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author luobaosong
 * @date 2024-07-22 18:29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PrizeParamDto extends PageDto {

    @Schema(description = "奖品名称")
    private String prizeName;

    @Schema(description = "奖品ID")
    private String prizeId;

}
