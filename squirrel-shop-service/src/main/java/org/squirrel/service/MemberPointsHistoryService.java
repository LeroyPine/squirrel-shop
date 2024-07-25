package org.squirrel.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.squirrel.mapper.MemberPointsHistoryMapper;
import org.squirrel.po.MemberPointsHistory;

import java.util.Date;

/**
 * @author luobaosong
 * @date 2024-07-23 16:43
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberPointsHistoryService {

    private final MemberPointsHistoryMapper memberPointsHistoryMapper;

    /**
     * 保存积分历史
     *
     * @param memberPointsHistory 积分历史
     */
    public Integer saveMemberPointsHistory(MemberPointsHistory memberPointsHistory) {
        memberPointsHistory.setCreateDate(new Date());
        memberPointsHistoryMapper.insert(memberPointsHistory);
        return memberPointsHistory.getId();
    }

    public MemberPointsHistory findMemberPointsHistoryById(Integer id) {
        return memberPointsHistoryMapper.selectById(id);
    }
}
