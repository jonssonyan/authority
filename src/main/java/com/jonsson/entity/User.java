package com.jonsson.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("user")
public class User implements Serializable {
    private static final long serialVersionUID = 1192800251115892576L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("parent_id")
    private Long parentId; // 上级id
    @TableField("domain")
    private Long domain; // 域名 通过域名判断他的上级是谁
    @TableField("role_id")
    private Long roleId; // 角色id
    @TableField("username")
    @NotBlank(message = "{user.name.notBlank}")
    private String username;
    @NotBlank(message = "{user.password.notBlank}")
    @TableField("password")
    private String password;
    @NotBlank(message = "{user.email.notBlank}")
    @Email(message = "{user.email.pattern}")
    @TableField("email")
    private String email;
    @TableField("qq")
    private String qq;
    @TableField("phone")
    private String phone;
    @TableField("state")
    private Integer state; // 状态 0/禁止 1/正常
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;

    // 用户的菜单列表，不同用户通过权限控制拥有不同的菜单
    @TableField(exist = false)
    private List<MenuList> menuLists;
}