package com.jonsson.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("category")
public class Category implements Serializable {
    private static final long serialVersionUID = -5888981197198625157L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("name")
    private String name;
    @TableField("parent_id")
    private Long parentId;
    @TableField("creator")
    private Long creator;
    @TableField("state") // 状态 0/下架 1/上架 默认上架
    private Integer state;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;

    @TableField(exist = false)
    private List<Category> Categorys;
}