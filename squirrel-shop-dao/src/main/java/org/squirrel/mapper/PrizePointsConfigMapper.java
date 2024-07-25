package org.squirrel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.squirrel.po.PrizePointsConfig;
import org.squirrel.po.UserRole;

import java.util.List;

/**
 * @author luobaosong
 * @date 2024-07-21 22:56
 */
@Mapper
public interface PrizePointsConfigMapper extends BaseMapper<PrizePointsConfig> {

    List<PrizePointsConfig> selectBatchPrizeIds(@Param("prizeIdList") List<String> prizeIdList);
}
