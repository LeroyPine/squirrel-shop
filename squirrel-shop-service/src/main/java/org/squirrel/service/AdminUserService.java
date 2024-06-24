package org.squirrel.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.ImmutableMap;
import exception.UserNameNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.squirrel.mapper.AdminUserMapper;
import org.squirrel.po.AdminUserInfo;

import javax.annotation.Resource;
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

    /**
     * 查看管理员信息
     */
    public List<AdminUserInfo> findAll() {
        List<AdminUserInfo> adminUserInfos = adminUserMapper.selectList(null);
        log.info("adminUserInfos : {}", adminUserInfos);
        return adminUserInfos;
    }


    public AdminUserInfo findByName(String username) {
        QueryWrapper<AdminUserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        AdminUserInfo adminUserInfo = adminUserMapper.selectOne(queryWrapper);
        return Optional.ofNullable(adminUserInfo).orElseThrow(() -> new UserNameNotFoundException(ImmutableMap.of("username", username)));
    }

}
