package com.jonsson.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("role")
public class Role implements Serializable {
    private static final long serialVersionUID = 5593937318976491954L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("parent_id")
    private String parentId;
    @TableField("name")
    @NotBlank
    private String name;
    @TableField("state")
    private Integer state;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    @TableField(exist = false)
    private List<Permission> permissions;
}