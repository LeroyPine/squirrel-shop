package org.squirrel.dto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author luobaosong
 * @date 2024-07-22 12:18
 */
@Data
public class PrizeDto {

    @Schema(description = "奖品ID")
    private String prizeId;

    @Schema(description = "奖品名称")
    private String prizeName;

    @Schema(description = "奖品图片")
    private String prizeImage;

    @Schema(description = "奖品描述")
    private String prizeDesc;

    @Schema(description = "奖品数量")
    private Integer prizeNum;

    @Schema(description = "修改时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    @Schema(description = "奖品消耗的积分")
    private Integer points;

}
