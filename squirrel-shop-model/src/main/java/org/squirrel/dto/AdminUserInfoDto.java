package org.squirrel.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author luobaosong
 * @date 2024-06-20 21:59
 */
@Data
@Schema(description = "用户信息")
@Builder
public class AdminUserInfoDto {

    private Integer userId;
    private String userName;
    private List<String> roles;
    @JsonIgnore
    private String token;
    @JsonIgnore
    private String refreshToken;
}
