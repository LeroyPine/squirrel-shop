package org.squirrel.biz;

import com.github.f4b6a3.uuid.UuidCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.squirrel.dto.PrizeDto;
import org.squirrel.dto.PrizePointsConfigDto;
import org.squirrel.enums.EnableEnum;
import org.squirrel.service.PrizePointsConfigService;
import org.squirrel.service.PrizeService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author luobaosong
 * @date 2024-07-22 21:09
 */
@Service
public class PrizeBizService {

    @Resource
    private PrizeService prizeService;
    @Resource
    private PrizePointsConfigService prizePointsConfigService;


    @Transactional(rollbackFor = Exception.class)
    public void savePrize(PrizeDto prizeDto) {
        // 保存奖品信息
        String prizeId = UuidCreator.getTimeOrdered().toString();
        prizeDto.setPrizeId(prizeId);
        prizeService.savePrize(prizeDto);

        //保存奖品积分配置信息
        PrizePointsConfigDto prizePointsConfig = PrizePointsConfigDto.builder()
                .prizeId(prizeId)
                .points(prizeDto.getPoints())
                .status(EnableEnum.Enable.getCode())
                .createDate(new Date())
                .build();
        prizePointsConfigService.savePrizePointsConfig(prizePointsConfig);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updatePrizeById(PrizeDto prizeDto) {
        // 修改奖品信息
        prizeService.updatePrizeById(prizeDto);

        // 修改奖品积分配置信息
        PrizePointsConfigDto prizePointsConfigDto = PrizePointsConfigDto.builder()
                .prizeId(prizeDto.getPrizeId())
                .points(prizeDto.getPoints())
                .build();
        prizePointsConfigService.updatePrizePointsConfig(prizePointsConfigDto);
    }
}
