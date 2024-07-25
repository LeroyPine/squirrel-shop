package org.squirrel.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.squirrel.mapper.MemberPointsMapper;
import org.squirrel.po.MemberPoints;

/**
 * @author luobaosong
 * @date 2024-07-23 16:43
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberPointsService {

    private final MemberPointsMapper memberPointsMapper;


    /**
     * 保存会员积分
     *
     * @param memberPoints 会员积分
     */
    public void saveMemberPoints(MemberPoints memberPoints) {
        memberPointsMapper.insert(memberPoints);
    }


    /**
     * 获取会员积分
     *
     * @param userId 用户id
     */
    public MemberPoints getMemberPointsByUserId(Integer userId) {
        QueryWrapper<MemberPoints> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        MemberPoints memberPoints = memberPointsMapper.selectOne(queryWrapper);
        log.info("getMemberPointsByUserId : {}", JSONObject.toJSONString(memberPoints));
        return memberPoints;
    }

    public void updateMemberPoints(MemberPoints memberPoints) {
        if (memberPoints.getPoints() <= 0) {
            throw new IllegalArgumentException("会员积分不能小于0!");
        }
        UpdateWrapper<MemberPoints> memberPointsUpdateWrapper = new UpdateWrapper<>();
        memberPointsUpdateWrapper.eq("user_id", memberPoints.getUserId());
        memberPointsMapper.update(memberPoints, memberPointsUpdateWrapper);
    }

    public int reduceMemberPoints(Integer userId, int consumePoints) {
        return memberPointsMapper.reduceMemberPoints(userId, consumePoints);
    }
}
