package com.jonsson.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("role_permission")
public class RolePermission implements Serializable {
    private static final long serialVersionUID = -3026205404689502862L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("role_id")
    private Long roleId;
    @TableField("permission_id")
    private Long permissionId;
    @TableField("state")
    private Integer state;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;

    @TableField(exist = false)
    private Role role;
    @TableField(exist = false)
    private Permission permission;
}
