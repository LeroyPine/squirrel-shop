package org.squirrel.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.squirrel.dto.MemberPointsListDto;
import org.squirrel.dto.MemberPointsListParamDto;
import org.squirrel.dto.SquirrelPageDto;
import org.squirrel.mapper.MemberPointsMapper;
import org.squirrel.po.MemberPoints;
import org.squirrel.po.Prize;

import java.math.BigDecimal;
import java.util.List;

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
        if (memberPoints.getPoints().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("会员积分不能小于0!");
        }
        UpdateWrapper<MemberPoints> memberPointsUpdateWrapper = new UpdateWrapper<>();
        memberPointsUpdateWrapper.eq("user_id", memberPoints.getUserId());
        memberPointsMapper.update(memberPoints, memberPointsUpdateWrapper);
    }

    public int reduceMemberPoints(Integer userId, BigDecimal consumePoints) {
        return memberPointsMapper.reduceMemberPoints(userId, consumePoints);
    }

    public List<MemberPoints> selectMemberPoints(List<Integer> userIds) {
        return memberPointsMapper.selectMemberPoints(userIds);
    }

    public SquirrelPageDto<MemberPointsListDto> getMemberPointsList(MemberPointsListParamDto memberPointsListParamDto) {
        log.info("memberPointsListParamDto : {}", JSONObject.toJSONString(memberPointsListParamDto));
        int page = memberPointsListParamDto.getPage();
        int pageSize = memberPointsListParamDto.getPageSize();
        Page<MemberPointsListDto> memberPointsPage = new Page<>(page, pageSize);
        Page<MemberPointsListDto> pointsListDtoListPage = memberPointsMapper.getMemberPointsList(memberPointsPage, memberPointsListParamDto);
        long total = pointsListDtoListPage.getTotal();
        return new SquirrelPageDto<>(page, pageSize, total, pointsListDtoListPage.getRecords());
    }
}
