package com.jonsson.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("role_menu_list")
public class RoleMenuList implements Serializable {
    private static final long serialVersionUID = 8055008415890637135L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long roleId;
    @TableField("menu_list_id")
    private Long MenuListId;
    @TableField("state")
    private Integer state;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;

    @TableField(exist = false)
    private Role role;
    @TableField(exist = false)
    private MenuList menuList;
}
