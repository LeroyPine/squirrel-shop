package org.squirrel.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author luobaosong
 * @date 2024-07-22 10:35
 */
@Data
@TableName("role_info")
public class RoleInfo {


    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * role_id
     */
    @TableField(value = "role_id")
    private String roleId;

    /**
     * role_name
     */
    @TableField(value = "role_name")
    private String roleName;

    /**
     * 角色状态 0 禁用 1 启用
     */
    @TableField(value = "role_status")
    private Integer roleStatus;

    /**
     * 创建时间
     */
    @TableField(value = "create_date")
    private Date createDate;

    /**
     * 修改时间
     */
    @TableField(value = "update_date")
    private Date updateDate;
}
