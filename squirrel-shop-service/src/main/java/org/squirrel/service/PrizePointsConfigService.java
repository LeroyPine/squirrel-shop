package org.squirrel.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.squirrel.dto.PrizePointsConfigDto;
import org.squirrel.enums.EnableEnum;
import org.squirrel.mapper.PrizePointsConfigMapper;
import org.squirrel.po.Prize;
import org.squirrel.po.PrizePointsConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author luobaosong
 * @date 2024-07-22 20:53
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PrizePointsConfigService {


    private final PrizePointsConfigMapper prizePointsConfigMapper;

    /**
     * 保存奖品配置信息
     *
     * @param prizePointsConfigDto 奖品配置
     */
    public void savePrizePointsConfig(PrizePointsConfigDto prizePointsConfigDto) {
        PrizePointsConfig prizePo = new PrizePointsConfig();
        BeanUtils.copyProperties(prizePointsConfigDto, prizePo);
        log.info("savePrizePointsConfig prize : {}", JSONObject.toJSONString(prizePo));
        prizePointsConfigMapper.insert(prizePo);
    }

    public List<PrizePointsConfigDto> findAllPrizePointsConfig() {
        List<PrizePointsConfig> prizePointsConfigs = prizePointsConfigMapper.selectList(null);
        if (CollectionUtils.isEmpty(prizePointsConfigs)) {
            return Collections.emptyList();
        }
        List<PrizePointsConfigDto> result = new ArrayList<>();
        for (PrizePointsConfig prizePointsConfig : prizePointsConfigs) {
            PrizePointsConfigDto prizePointsConfigDto = new PrizePointsConfigDto();
            BeanUtils.copyProperties(prizePointsConfig, prizePointsConfigDto);
            result.add(prizePointsConfigDto);
        }
        log.info("findAllPrizePointsConfig res:{}", JSONObject.toJSONString(result));
        return result;
    }

    public void updatePrizePointsConfig(PrizePointsConfigDto prizePointsConfigDto) {
        PrizePointsConfig prizePointsConfig = new PrizePointsConfig();
        BeanUtils.copyProperties(prizePointsConfigDto, prizePointsConfig);
        log.info("updatePrizePointsConfig prize : {}", JSONObject.toJSONString(prizePointsConfig));
        UpdateWrapper<PrizePointsConfig> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("prize_id", prizePointsConfigDto.getPrizeId());
        prizePointsConfigMapper.update(prizePointsConfig, updateWrapper);
    }
}
