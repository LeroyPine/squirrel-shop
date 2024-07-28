package org.squirrel.po;

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
public class Product {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 商品id
     */
    @TableField(value = "product_id")
    private String productId;

    /**
     * 商品名称
     */
    @TableField(value = "product_name")
    private String productName;

    /**
     * 商品状态
     */
    @TableField(value = "product_status")
    private Integer productStatus;


    /**
     * 商品图片
     */
    @TableField(value = "product_image")
    private String productImage;

    /**
     * 商品描述
     */
    @TableField(value = "product_desc")
    private String productDesc;

    /**
     * 商品数量
     */
    @TableField(value = "product_num")
    private Integer productNum;

    /**
     * 产品金额
     */
    @TableField(value = "product_money")
    private BigDecimal productMoney;

    /**
     * 创建时间
     */
    @TableField(value = "create_date")
    private Date createDate;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @TableField(value = "update_date")
    private Date updateDate;
}
