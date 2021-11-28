package com.jonssonyan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("`permission`")
@ApiModel("权限")
public class Permission extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 4551749462814959751L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Integer state;
    private Date createTime;
    private Date updateTime;
}