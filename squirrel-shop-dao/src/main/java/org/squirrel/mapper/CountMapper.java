package org.squirrel.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.squirrel.dto.UserRankDto;
import org.squirrel.dto.WeeklyMoneyDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author luobaosong
 * @date 2024-07-28 18:24
 */
@Mapper
public interface CountMapper {


    Integer countUserByType(@Param("type") Integer type);

    Integer countPointByType(@Param("type") Integer type);


    BigDecimal sumMoneyByType(@Param("type") Integer type);


    List<WeeklyMoneyDto> countWeeklyMoney();

    List<UserRankDto> getUserRank();
}
