package com.jonssonyan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("`role_menu_list`")
@ApiModel("角色和菜单关系")
public class RoleMenuList extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 8055008415890637135L;

    @ApiModelProperty("角色id")
    private Long roleId;
    @ApiModelProperty("菜单id")
    private Long menuListId;
    @ApiModelProperty("状态")
    private Integer state;

    @TableField(exist = false)
    private Role role;
    @TableField(exist = false)
    private MenuList menuList;
}
