package org.squirrel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.squirrel.po.MemberPointsHistoryDetail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author luobaosong
 * @date 2024-07-28 10:26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberPointsHistoryListDto {

    private Integer id;

    private Integer userId;

    private String userName;

    private BigDecimal beforePoints;

    private BigDecimal afterPoints;

    private BigDecimal amount;

    private BigDecimal redeemedPoints;

    private String changeDesc;

    private Integer changeType;

    private String remark;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;


    private List<MemberPointsHistoryDetail> detailList;


}
