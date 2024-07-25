package org.squirrel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.squirrel.dto.PrizeDto;
import org.squirrel.po.Prize;

/**
 * @author luobaosong
 * @date 2024-07-21 22:56
 */
@Mapper
public interface PrizeMapper extends BaseMapper<Prize> {


    @Select("select p.prize_id,p.prize_num,p.prize_name,p.prize_image,p.prize_desc,pp.points from prize p " +
            "left join prize_points_config pp on p.prize_id = pp.prize_id where p.prize_id = #{prizeId}")
    @ResultType(PrizeDto.class)
    PrizeDto selectPrizeByPrizeId(@Param("prizeId") String prizeId);


    @Update("update prize set prize_num = prize_num - #{num} where (prize_num - #{num}) >= 0 and prize_id = #{prizeId}")
    int reducePrizeNum(@Param("prizeId") String prizeId, @Param("num") Integer num);
}
