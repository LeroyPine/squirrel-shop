package org.squirrel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.squirrel.po.AdminUserInfo;

/**
 * @author luobaosong
 * @date 2024-06-02 21:50
 */
@Mapper
public interface AdminUserMapper extends BaseMapper<AdminUserInfo> {
}
