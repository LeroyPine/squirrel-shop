package org.squirrel.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author luobaosong
 * @date 2024-06-23 16:17
 */
@Data
@TableName("user_role")
public class UserRole {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "user_id")
    private Integer userId;
    @TableField(value = "role_id")
    private String roleId;
    @TableField(value = "create_date")
    private Date createDate;
}
