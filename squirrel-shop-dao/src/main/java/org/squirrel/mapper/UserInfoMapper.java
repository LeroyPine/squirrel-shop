package org.squirrel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.squirrel.dto.UserInfoDto;
import org.squirrel.po.UserInfo;

/**
 * @author luobaosong
 * @date 2024-06-02 21:50
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {


    @Select("select u.user_id,u.user_name,u.avatar,u.phone,u.update_date,mp.points " +
            "from user_info u left join member_points mp on  u.user_id = mp.user_id where u.user_id = #{userId} ")
    @ResultType(UserInfoDto.class)
    UserInfoDto selectUserInfoByUserId(@Param("userId") Integer userId);

}
