package org.squirrel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.squirrel.po.MemberPoints;

/**
 * @author luobaosong
 * @date 2024-07-21 22:56
 */
@Mapper
public interface MemberPointsMapper extends BaseMapper<MemberPoints> {

    @Update("update member_points set points = points - #{consumePoints} where user_id = #{userId} and points - #{consumePoints} >=0")
    int reduceMemberPoints(@Param("userId") Integer userId, @Param("consumePoints") int consumePoints);
}
