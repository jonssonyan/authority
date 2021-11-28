package com.jonssonyan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("`role`")
@ApiModel("角色")
public class Role extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 5593937318976491954L;

    @ApiModelProperty("父级id")
    private String parentId;
    @NotBlank
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("状态")
    private Integer state;

    @TableField(exist = false)
    private List<Permission> permissions;
}