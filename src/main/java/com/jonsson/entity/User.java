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
@TableName("`user`")
public class User implements Serializable {
    private static final long serialVersionUID = 1192800251115892576L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long parentId; // 上级id
    private String path; // 路径
    private Integer level; // 等级
    private String domain; // 域名
    private Long roleId; // 角色id
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;
    private String qq;
    private String phone;
    private Integer state; // 状态 0/禁止 1/正常
    private Date createTime;
    private Date updateTime;

    // 用户的菜单列表，不同用户通过权限控制拥有不同的菜单
    @TableField(exist = false)
    private List<MenuList> menuLists;
}