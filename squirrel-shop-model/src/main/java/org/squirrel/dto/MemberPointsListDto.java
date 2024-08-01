package org.squirrel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author luobaosong
 * @date 2024-07-28 10:26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberPointsListDto{

    private Integer userId;

    private String userName;

    private String phone;

    private BigDecimal points;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;


}
