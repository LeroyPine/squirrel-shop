package org.squirrel.dto;

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
public class PrizePointsConfigDto {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 奖品id
     */
    private String prizeId;

    /**
     * 消耗积分
     */
    private Integer points;

    /**
     * 奖品状态 1正常 0禁用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date updateDate;
}
