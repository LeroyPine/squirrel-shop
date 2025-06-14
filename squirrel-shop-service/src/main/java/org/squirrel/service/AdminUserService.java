package org.squirrel.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.ImmutableMap;
import org.squirrel.dto.AdminUserDetailDto;
import org.squirrel.exception.UserNameNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.squirrel.mapper.AdminUserMapper;
import org.squirrel.po.AdminUserInfo;

import java.util.List;
import java.util.Optional;

/**
 * @author luobaosong
 * @date 2024-06-02 22:02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final AdminUserMapper adminUserMapper;

    public AdminUserInfo findByName(String username) {
        QueryWrapper<AdminUserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        AdminUserInfo adminUserInfo = adminUserMapper.selectOne(queryWrapper);
        return Optional.ofNullable(adminUserInfo).orElseThrow(UserNameNotFoundException::new);
    }

    public AdminUserInfo findById(Integer userId) {
        QueryWrapper<AdminUserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return adminUserMapper.selectOne(queryWrapper);
    }


}
