package org.squirrel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luobaosong
 * @date 2024-07-24 23:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail {

    private Integer userId;
    private String userName;

}
