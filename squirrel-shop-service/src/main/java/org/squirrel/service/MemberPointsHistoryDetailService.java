package org.squirrel.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.squirrel.mapper.MemberPointsHistoryDetailMapper;
import org.squirrel.po.MemberPointsHistoryDetail;

import java.util.Date;
import java.util.List;

/**
 * @author luobaosong
 * @date 2024-07-23 19:53
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberPointsHistoryDetailService {

    private final MemberPointsHistoryDetailMapper memberPointsHistoryDetailMapper;

    /**
     * 保存会员积分历史明细
     *
     * @param memberPointsHistoryDetail 会员积分历史明细
     */
    public void saveMemberPointsHistoryDetail(MemberPointsHistoryDetail memberPointsHistoryDetail) {
        memberPointsHistoryDetail.setCreateDate(new Date());
        memberPointsHistoryDetailMapper.insert(memberPointsHistoryDetail);
    }

    public List<MemberPointsHistoryDetail> findMemberPointsHistoryDetailByHistoryId(Integer historyId) {
        QueryWrapper<MemberPointsHistoryDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("history_id", historyId);
        return memberPointsHistoryDetailMapper.selectList(queryWrapper);
    }

    public void updateMemberPointsHistoryDetail(MemberPointsHistoryDetail memberPointsHistoryDetail) {
        memberPointsHistoryDetailMapper.updateById(memberPointsHistoryDetail);
    }

}
