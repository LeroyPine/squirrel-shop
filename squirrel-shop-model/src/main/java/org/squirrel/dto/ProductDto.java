package org.squirrel.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author luobaosong
 * @date 2024-07-25 19:16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 商品id
     */
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品状态
     */
    private Integer productStatus;


    /**
     * 商品图片
     */
    private String productImage;

    /**
     * 商品描述
     */
    private String productDesc;

    /**
     * 商品数量
     */
    private Integer productNum;

    /**
     * 产品金额
     */
    private BigDecimal productMoney;

}
