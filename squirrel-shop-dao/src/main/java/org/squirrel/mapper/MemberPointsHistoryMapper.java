package org.squirrel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.squirrel.dto.MemberPointsHistoryListDto;
import org.squirrel.dto.MemberPointsListParamDto;
import org.squirrel.po.MemberPointsHistory;

/**
 * @author luobaosong
 * @date 2024-07-21 22:56
 */
@Mapper
public interface MemberPointsHistoryMapper extends BaseMapper<MemberPointsHistory> {


    Page<MemberPointsHistoryListDto> findMemberPointsHistory(Page<MemberPointsHistoryListDto> memberPointsPage, @Param("memberPoints") MemberPointsListParamDto memberPointsListParamDto);
}
