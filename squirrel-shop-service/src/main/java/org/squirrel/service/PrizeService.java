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
import org.squirrel.po.Prize;

import java.util.*;

/**
 * @author luobaosong
 * @date 2024-07-21 22:55
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PrizeService {

    private final PrizeMapper prizeMapper;

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
        queryWrapper.eq(StringUtils.isNotBlank(prizeParamDto.getPrizeName()), "prize_name", prizeParamDto.getPrizeName());
        queryWrapper.eq(StringUtils.isNotBlank(prizeParamDto.getPrizeId()), "prize_id", prizeParamDto.getPrizeId());
        Page<Prize> prizePageInfo = prizeMapper.selectPage(prizePage, queryWrapper);
        long total = prizePageInfo.getTotal();
        if (total == 0) {
            return new SquirrelPageDto<>(page, pageSize, total);
        }
        List<Prize> records = prizePageInfo.getRecords();
        List<PrizeDto> prizeDtoList = new ArrayList<>();
        for (Prize prize : records) {
            PrizeDto prizeDto = new PrizeDto();
            BeanUtils.copyProperties(prize, prizeDto);
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
        QueryWrapper<Prize> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("prize_id", prizeId);
        Prize prize = prizeMapper.selectOne(queryWrapper);
        if (Objects.isNull(prize)) {
            return null;
        }
        PrizeDto prizeDto = new PrizeDto();
        BeanUtils.copyProperties(prize, prizeDto);
        log.info("findPrizeById prize:{}", JSONObject.toJSONString(prizeDto));
        return prizeDto;
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
    public int reducePrizeNum(String prizeId) {
        UpdateWrapper<Prize> updateWrapper = new UpdateWrapper<>();
        String sql = String.format("update prize set prize_num = prize_num -1 where prize_num > 0 and id = %s", prizeId);
        updateWrapper.setSql(sql);
        return prizeMapper.update(null, updateWrapper);
    }
}
