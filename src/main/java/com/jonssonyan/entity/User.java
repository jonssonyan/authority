package com.jonssonyan.entity;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("`user`")
@ApiModel("用户")
public class User extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1192800251115892576L;
    private Long parentId; // 上级id
    @TableField("`path`")
    @ApiModelProperty("路径")
    private String path;
    @ApiModelProperty("等级")
    private Integer level;
    @ApiModelProperty("角色id")
    private Long roleId;
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty("用户名")
    private String username;
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty("密码")
    private String password;
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("qq")
    private String qq;
    @ApiModelProperty("联系电话")
    private String phone;
    @ApiModelProperty("状态 0/禁止 1/正常")
    private Integer state;

    // 用户的菜单列表，不同用户通过权限控制拥有不同的菜单
    @TableField(exist = false)
    private List<MenuList> menuLists;

    public final void parent(@NotNull User user) {
        this.parentId = user.getId();
        this.path = (user.getPath() == null ? "" : user.getPath()) + user.getId() + "-";
        this.level = StrUtil.count(this.path, "-");
    }
}