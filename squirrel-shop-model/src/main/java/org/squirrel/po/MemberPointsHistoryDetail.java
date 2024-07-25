package org.squirrel.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author luobaosong
 * @date 2024-07-23 17:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberPointsHistoryDetail {


    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * history_id
     */
    @TableField(value = "history_id")
    private Integer historyId;

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
     * 商品数量
     */
    @TableField(value = "product_num")
    private Integer productNum;

    /**
     * 单个商品积分
     */
    @TableField(value = "product_points")
    private Integer productPoints;

    /**
     * 单个商品金额
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
    @TableField(value = "update_date")
    private Date updateDate;
}
