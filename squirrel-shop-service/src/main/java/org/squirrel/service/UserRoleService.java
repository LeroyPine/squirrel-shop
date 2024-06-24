package org.squirrel.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.squirrel.mapper.UserRoleMapper;
import org.squirrel.po.UserRole;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luobaosong
 * @date 2024-06-23 16:36
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleMapper userRoleMapper;

    public List<String> findRolesByUserId(Integer userId) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<UserRole> userRoles = userRoleMapper.selectList(queryWrapper);
        return userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
    }

}
