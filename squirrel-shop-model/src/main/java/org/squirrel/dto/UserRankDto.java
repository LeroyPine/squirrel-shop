package org.squirrel.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author luobaosong
 * @date 2024-07-28 18:36
 */
@Data
public class UserRankDto {

    private String userName;

    private String phone;

    private String address;

    private BigDecimal money;
}
