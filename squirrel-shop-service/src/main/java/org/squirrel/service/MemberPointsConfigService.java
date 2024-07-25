package org.squirrel.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.squirrel.mapper.MemberPointsConfigMapper;
import org.squirrel.po.MemberPointsConfig;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author luobaosong
 * @date 2024-07-23 16:43
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberPointsConfigService {

    private final MemberPointsConfigMapper memberPointsConfigMapper;


    public List<MemberPointsConfig> findAllMemberPointsConfig() {
        return memberPointsConfigMapper.selectList(null);
    }

    public MemberPointsConfig findMemberPointsConfigByType(Integer configType) {
        List<MemberPointsConfig> memberPointsConfigs = findAllMemberPointsConfig();
        return memberPointsConfigs.stream().filter(s -> Objects.equals(s.getConfigType(), configType)).findAny().orElse(null);
    }


}
