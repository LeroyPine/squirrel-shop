package org.squirrel.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author luobaosong
 * @date 2024-07-22 09:45
 */
@Data
@TableName("member_points_config")
public class MemberPointsConfig {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 金额
     */
    @TableField(value = "money")
    private BigDecimal money;

    /**
     * 积分
     */
    @TableField(value = "points")
    private Integer points;

    /**
     * 状态 1有效 0无效
     */
    @TableField(value = "status")
    private Integer status;

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
