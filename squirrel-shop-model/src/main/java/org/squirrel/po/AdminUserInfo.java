package org.squirrel.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author luobaosong
 * @date 2024-06-02 21:27
 */
@Data
@TableName("admin_user_info")
public class AdminUserInfo {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "user_id")
    private Integer userId;

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "nick_name")
    private String nickName;

    @TableField(value = "user_pwd")
    private String userPwd;

    @TableField(value = "avatar")
    private String avatar;

    @TableField(value = "update_date")
    private String updateDate;


}
