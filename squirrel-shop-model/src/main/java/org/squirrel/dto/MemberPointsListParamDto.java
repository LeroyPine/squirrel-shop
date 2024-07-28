package org.squirrel.dto;

import lombok.*;

import java.util.Date;

/**
 * @author luobaosong
 * @date 2024-07-28 10:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberPointsListParamDto extends PageDto{

    private String userName;

    private String phone;

    private Integer changeType;

    private Date startTime;

    private Date endTime;

}
