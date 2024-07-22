package org.squirrel.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author luobaosong
 * @date 2024-07-22 09:41
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("prize_points_config")
public class PrizePointsConfig {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 奖品id
     */
    @TableField(value = "prize_id")
    private String prizeId;

    /**
     * 消耗积分
     */
    @TableField(value = "points")
    private Integer points;

    /**
     * 奖品状态 1正常 0禁用
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
