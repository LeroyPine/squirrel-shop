package org.squirrel.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.squirrel.mapper.AdminUserMapper;
import org.squirrel.po.AdminUserInfo;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author luobaosong
 * @date 2024-06-02 22:02
 */
@Slf4j
@Service
public class AdminUserService {

    @Resource
    private AdminUserMapper adminUserMapper;

    /**
     * 查看管理员信息
     */
    public List<AdminUserInfo> findAll() {
        List<AdminUserInfo> adminUserInfos = adminUserMapper.selectList(null);
        log.info("adminUserInfos : {}", adminUserInfos);
        return adminUserInfos;
    }

}
