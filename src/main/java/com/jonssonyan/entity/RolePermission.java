package com.jonssonyan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("`role_permission`")
@ApiModel("角色和权限关系")
public class RolePermission extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -3026205404689502862L;
    @ApiModelProperty("角色id")
    private Long roleId;
    @ApiModelProperty("权限id")
    private Long permissionId;
    @ApiModelProperty("状态")
    private Integer state;

    @TableField(exist = false)
    private Role role;
    @TableField(exist = false)
    private Permission permission;
}
