package org.squirrel.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author luobaosong
 * @date 2024-07-28 18:36
 */
@Data
public class WeeklyMoneyDto {

    private String date;

    private BigDecimal amount;
}
