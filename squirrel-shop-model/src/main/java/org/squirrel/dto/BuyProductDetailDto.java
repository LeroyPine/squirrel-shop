package org.squirrel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author luobaosong
 * @date 2024-07-25 16:07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuyProductDetailDto {

    @Schema(description = "用户ID")
    private Integer userId;

    @Schema(description = "消费记录ID")
    private Integer historyId;

    @Schema(description = "消费记录明细")
    private List<ProductDetailDto> productDetailDtoList;

    @Schema(description = "补充购物详情类型 1:选择商品 2:手动录入")
    private Integer buyDetailType;


    @Data
    public static class ProductDetailDto {

        @Schema(description = "商品ID")
        private String productId;

        @Schema(description = "商品名称")
        private String productName;

        @Schema(description = "商品数量")
        private Integer productNum;

        @Schema(description = "商品单价")
        private BigDecimal productMoney;
    }
}
