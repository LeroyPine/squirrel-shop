package org.squirrel.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author luobaosong
 * @date 2024-07-22 09:45
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("member_points_history")
public class MemberPointsHistory {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 1:消费 2:兑奖
     */
    @TableField(value = "change_type")
    private Integer changeType;

    /**
     * 变更前积分
     */
    @TableField(value = "before_points")
    private BigDecimal beforePoints;

    /**
     * 变更后积分
     */
    @TableField(value = "after_points")
    private BigDecimal afterPoints;

    /**
     * 变更描述
     */
    @TableField(value = "change_desc")
    private String changeDesc;


    /**
     * 消费金额
     */
    @TableField(value = "amount")
    private BigDecimal amount;

    /**
     * 兑换积分
     */
    @TableField(value = "redeemed_points")
    private BigDecimal redeemedPoints;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

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
