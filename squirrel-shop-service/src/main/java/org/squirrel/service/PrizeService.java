package org.squirrel.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.squirrel.dto.PrizeDto;
import org.squirrel.dto.PrizeParamDto;
import org.squirrel.dto.SquirrelPageDto;
import org.squirrel.mapper.PrizeMapper;
import org.squirrel.mapper.PrizePointsConfigMapper;
import org.squirrel.po.Prize;
import org.squirrel.po.PrizePointsConfig;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author luobaosong
 * @date 2024-07-21 22:55
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PrizeService {

    private final PrizeMapper prizeMapper;

    private final PrizePointsConfigMapper prizePointsConfigMapper;

    /**
     * 保存奖品
     *
     * @param prize 奖品
     */
    public void savePrize(PrizeDto prize) {
        Prize prizePo = Prize.builder()
                .prizeId(prize.getPrizeId())
                .prizeDesc(prize.getPrizeDesc())
                .prizeNum(prize.getPrizeNum())
                .prizeName(prize.getPrizeName())
                .prizeImage(prize.getPrizeImage())
                .createDate(new Date())
                .build();
        log.info("savePrize prize : {}", JSONObject.toJSONString(prize));
        prizeMapper.insert(prizePo);
    }


    /**
     * 获取所有奖品
     */
    public SquirrelPageDto<PrizeDto> findAllPrizes(PrizeParamDto prizeParamDto) {
        int page = prizeParamDto.getPage();
        int pageSize = prizeParamDto.getPageSize();
        Page<Prize> prizePage = new Page<>(page, pageSize);
        QueryWrapper<Prize> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(prizeParamDto.getPrizeName()), "prize_name", prizeParamDto.getPrizeName());
        queryWrapper.eq(StringUtils.isNotBlank(prizeParamDto.getPrizeId()), "prize_id", prizeParamDto.getPrizeId());
        queryWrapper.orderByDesc("update_date");
        Page<Prize> prizePageInfo = prizeMapper.selectPage(prizePage, queryWrapper);
        long total = prizePageInfo.getTotal();
        if (total == 0) {
            return new SquirrelPageDto<>(page, pageSize, total);
        }
        List<Prize> records = prizePageInfo.getRecords();
        List<PrizeDto> prizeDtoList = new ArrayList<>();

        List<String> prizeIdList = records.stream().map(Prize::getPrizeId).collect(Collectors.toList());
        List<PrizePointsConfig> prizePointsConfigs = prizePointsConfigMapper.selectBatchPrizeIds(prizeIdList);
        Map<String, PrizePointsConfig> prizeIdPointsMap = prizePointsConfigs.stream().collect(Collectors.toMap(PrizePointsConfig::getPrizeId, Function.identity()));

        for (Prize prize : records) {
            PrizeDto prizeDto = new PrizeDto();
            PrizePointsConfig prizePointsConfig = prizeIdPointsMap.get(prize.getPrizeId());
            BeanUtils.copyProperties(prize, prizeDto);
            prizeDto.setPoints(prizePointsConfig.getPoints());
            prizeDtoList.add(prizeDto);
        }
        SquirrelPageDto<PrizeDto> prizeDtoPage = new SquirrelPageDto<>(page, pageSize, total, prizeDtoList);
        log.info("findAllPrizes prize : {}", JSONObject.toJSONString(prizeDtoPage));
        return prizeDtoPage;
    }


    /**
     * 根据奖品ID获取奖品
     *
     * @param prizeId 奖品ID
     */
    public PrizeDto findByPrizeId(String prizeId) {
        if (StringUtils.isBlank(prizeId)) {
            return null;
        }
        PrizeDto prize = prizeMapper.selectPrizeByPrizeId(prizeId);
        log.info("findPrizeById prize:{}", JSONObject.toJSONString(prize));
        return prize;
    }

    /**
     * 修改奖品信息
     *
     * @param prizeDto 奖品信息
     */
    public void updatePrizeById(PrizeDto prizeDto) {
        Prize prize = new Prize();
        BeanUtils.copyProperties(prizeDto, prize);
        UpdateWrapper<Prize> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("prize_id", prizeDto.getPrizeId());
        log.info("updatePrizeById prize : {}", JSONObject.toJSONString(prize));
        prizeMapper.update(prize, updateWrapper);
    }


    /**
     * 减少奖品数量
     *
     * @param prizeId 奖品ID
     */
    public int reducePrizeNum(String prizeId, Integer num) {
        return prizeMapper.reducePrizeNum(prizeId, num);
    }
}
