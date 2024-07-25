package org.squirrel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author luobaosong
 * @date 2024-07-25 16:02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuyProductDto {

    @Schema(description = "用户ID")
    private Integer userId;

    @Schema(description = "消费总金额")
    private BigDecimal price;

    @Schema(description = "备注")
    private String remark;
}
