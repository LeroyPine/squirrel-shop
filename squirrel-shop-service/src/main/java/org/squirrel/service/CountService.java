package org.squirrel.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.squirrel.dto.UserRankDto;
import org.squirrel.dto.WeeklyMoneyDto;
import org.squirrel.mapper.CountMapper;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author luobaosong
 * @date 2024-07-28 18:18
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CountService {

    private final CountMapper countMapper;


    public BigDecimal sumMoneyByType(Integer type) {
        // 1是天纬度,2是周围度
        return countMapper.sumMoneyByType(type);
    }

    public Integer countUserByType(Integer type) {
        // 1是天纬度,2是周围度
        return countMapper.countUserByType(type);
    }


    public Integer countPointByType(Integer type) {
        // 1是天纬度,2是周围度
        return countMapper.countPointByType(type);
    }

    public List<WeeklyMoneyDto> countWeeklyMoney() {
        // 1是天纬度,2是周围度
        return countMapper.countWeeklyMoney();
    }

    public List<UserRankDto> getUserRank() {
        return countMapper.getUserRank();
    }
}
