package org.squirrel.biz;

import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.squirrel.dto.SquirrelPageDto;
import org.squirrel.dto.UserInfoDto;
import org.squirrel.dto.UserInfoParamDto;
import org.squirrel.enums.MemberChangeTypeEnum;
import org.squirrel.po.MemberPoints;
import org.squirrel.po.MemberPointsHistory;
import org.squirrel.po.UserInfo;
import org.squirrel.service.MemberPointsHistoryService;
import org.squirrel.service.MemberPointsService;
import org.squirrel.service.UserInfoService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author luobaosong
 * @date 2024-07-23 20:02
 */
@Service
public class UserInfoBizService {

    @Resource
    private UserInfoService userInfoService;
    @Resource
    private MemberPointsService memberPointsService;
    @Resource
    private MemberPointsHistoryService memberPointsHistoryService;


    /**
     * 保存用户信息
     *
     * @param userInfoDto 用户信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveUserInfo(UserInfoDto userInfoDto) {
        // 保存用户信息
        Integer userId = userInfoService.saveUserInfo(userInfoDto);

        // 初始化用户积分
        MemberPoints memberPoints = MemberPoints.builder()
                .userId(userId)
                .points(userInfoDto.getPoints())
                .createDate(new Date())
                .build();
        memberPointsService.saveMemberPoints(memberPoints);

        // 存入用户积分变更记录表
        MemberPointsHistory pointsHistory = MemberPointsHistory.builder()
                .changeType(MemberChangeTypeEnum.UPDATE_BASE_INFO.getCode())
                .userId(userId)
                .beforePoints(BigDecimal.ZERO)
                .afterPoints(userInfoDto.getPoints())
                .changeDesc("管理员初始化用户信息,积分更新为:" + userInfoDto.getPoints())
                .build();
        memberPointsHistoryService.saveMemberPointsHistory(pointsHistory);
    }


    /**
     * 修改用户信息
     *
     * @param userInfoDto 用户信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(UserInfoDto userInfoDto) {

        // 修改用户信息
        userInfoService.updateUserInfo(userInfoDto);

        // 查询用户积分,如果与当前积分不一致进行更新并记录
        MemberPoints memberPoints = memberPointsService.getMemberPointsByUserId(userInfoDto.getUserId());
        if (memberPoints == null) {
            throw new IllegalArgumentException("当前用户积分未进行初始化!");
        }

        // 如果积分相同则不进行更新
        BigDecimal points = memberPoints.getPoints();
        if (points.compareTo(userInfoDto.getPoints()) == 0) {
            return;
        }
        MemberPoints updateMemberPoints = MemberPoints.builder()
                .userId(userInfoDto.getUserId())
                .points(userInfoDto.getPoints())
                .build();
        memberPointsService.updateMemberPoints(updateMemberPoints);

        // 存入用户积分变更记录表
        MemberPointsHistory pointsHistory = MemberPointsHistory.builder()
                .changeType(MemberChangeTypeEnum.UPDATE_BASE_INFO.getCode())
                .userId(userInfoDto.getUserId())
                .beforePoints(points)
                .afterPoints(userInfoDto.getPoints())
                .changeDesc("管理员修改用户信息,积分更新为:" + userInfoDto.getPoints())
                .build();
        memberPointsHistoryService.saveMemberPointsHistory(pointsHistory);
    }

    public SquirrelPageDto<UserInfoDto> getAllUserInfo(UserInfoParamDto userInfoParamDto) {
        SquirrelPageDto<UserInfo> allUserInfoPage = userInfoService.getAllUserInfo(userInfoParamDto);
        long page = allUserInfoPage.getPage();
        long pageSize = allUserInfoPage.getPageSize();
        long total = allUserInfoPage.getTotal();
        List<UserInfo> records = allUserInfoPage.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return new SquirrelPageDto<>(page, pageSize, total, null);
        }
        List<Integer> userIdList = records.stream().map(UserInfo::getUserId).collect(Collectors.toList());
        List<MemberPoints> memberPoints = memberPointsService.selectMemberPoints(userIdList);
        Map<Integer, BigDecimal> userIdPointsMap = memberPoints.stream().collect(Collectors.toMap(MemberPoints::getUserId, MemberPoints::getPoints));
        List<UserInfoDto> userInfoDtoList = Lists.newArrayList();
        records.forEach(s -> {
            UserInfoDto userInfoDto = new UserInfoDto();
            BeanUtils.copyProperties(s, userInfoDto);
            userInfoDto.setPoints(userIdPointsMap.get(s.getUserId()));
            userInfoDtoList.add(userInfoDto);
        });
        return new SquirrelPageDto<>(page, pageSize, total, userInfoDtoList);
    }
}
