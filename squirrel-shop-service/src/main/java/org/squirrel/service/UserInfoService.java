package org.squirrel.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.squirrel.dto.PrizeDto;
import org.squirrel.dto.SquirrelPageDto;
import org.squirrel.dto.UserInfoDto;
import org.squirrel.dto.UserInfoParamDto;
import org.squirrel.mapper.UserInfoMapper;
import org.squirrel.po.Prize;
import org.squirrel.po.UserInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author luobaosong
 * @date 2024-07-23 15:57
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoMapper userInfoMapper;

    /**
     * 保存用户信息
     *
     * @param userInfoDto 用户信息
     */
    public Integer saveUserInfo(UserInfoDto userInfoDto) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoDto, userInfo);
        userInfo.setCreateDate(new Date());
        log.info("saveUserInfo info:{}", JSONObject.toJSONString(userInfo));
        userInfoMapper.insert(userInfo);
        return userInfo.getUserId();
    }

    /**
     * 修改用户信息
     *
     * @param userInfoDto 用户信息
     */
    public void updateUserInfo(UserInfoDto userInfoDto) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoDto, userInfo);
        userInfoMapper.updateById(userInfo);
    }

    /**
     * 获取所有用户信息
     */
    public SquirrelPageDto<UserInfo> getAllUserInfo(UserInfoParamDto userInfoParamDto) {
        int page = userInfoParamDto.getPage();
        int pageSize = userInfoParamDto.getPageSize();
        Page<UserInfo> userPage = new Page<>(page, pageSize);
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(userInfoParamDto.getUserName()), "user_name", userInfoParamDto.getUserName());
        queryWrapper.eq(StringUtils.isNotBlank(userInfoParamDto.getPhone()), "phone", userInfoParamDto.getPhone());
        queryWrapper.eq(Objects.nonNull(userInfoParamDto.getUserId()), "user_id", userInfoParamDto.getUserId());
        Page<UserInfo> userPageInfo = userInfoMapper.selectPage(userPage, queryWrapper);
        long total = userPageInfo.getTotal();
        if (total == 0) {
            return new SquirrelPageDto<>(page, pageSize, total);
        }
        List<UserInfo> records = userPageInfo.getRecords();
        return new SquirrelPageDto<>(page, pageSize, total, records);
    }

    public UserInfoDto findUserInfoByUserId(Integer userId) {
        return userInfoMapper.selectUserInfoByUserId(userId);
    }
}
