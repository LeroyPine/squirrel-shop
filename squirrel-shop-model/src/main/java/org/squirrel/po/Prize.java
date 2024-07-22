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
 * @date 2024-07-21 22:56
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("prize")
public class Prize {

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
     * 奖品名称
     */
    @TableField(value = "prize_name")
    private String prizeName;

    /**
     * 奖品图片
     */
    @TableField(value = "prize_image")
    private String prizeImage;

    /**
     * 奖品描述
     */
    @TableField(value = "prize_desc")
    private String prizeDesc;

    /**
     * 奖品数量
     */
    @TableField(value = "prize_num")
    private Integer prizeNum;

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
