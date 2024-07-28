package org.squirrel.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.squirrel.dto.MemberPointsHistoryListDto;
import org.squirrel.dto.MemberPointsListDto;
import org.squirrel.dto.MemberPointsListParamDto;
import org.squirrel.dto.SquirrelPageDto;
import org.squirrel.mapper.MemberPointsHistoryMapper;
import org.squirrel.po.MemberPoints;
import org.squirrel.po.MemberPointsHistory;
import org.squirrel.po.Prize;

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

    public SquirrelPageDto<MemberPointsHistoryListDto> findMemberPointsHistory(MemberPointsListParamDto memberPointsListParamDto) {
        int page = memberPointsListParamDto.getPage();
        int pageSize = memberPointsListParamDto.getPageSize();
        Page<MemberPointsHistoryListDto> memberPointsPage = new Page<>(page, pageSize);
        Page<MemberPointsHistoryListDto> pointsListDtoListPage = memberPointsHistoryMapper.findMemberPointsHistory(memberPointsPage, memberPointsListParamDto);
        long total = pointsListDtoListPage.getTotal();
        return new SquirrelPageDto<>(page, pageSize, total, pointsListDtoListPage.getRecords());

    }
}
