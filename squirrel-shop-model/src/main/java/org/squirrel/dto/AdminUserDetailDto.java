package org.squirrel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author luobaosong
 * @date 2024-07-27 10:09
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserDetailDto {

    private Integer userId;
    private String userName;
    private List<String> roles;
    private String avatar;
}
