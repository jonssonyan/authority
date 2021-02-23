package com.jonsson.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("product")
public class Product implements Serializable {
    private static final long serialVersionUID = -54169886181194401L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("category_id")
    private Long categoryId;
    @TableField("name")
    private String name;
    @TableField("price")
    private BigDecimal price;
    @TableField("creator")
    private Long creator;
    @TableField("state")
    private Integer state;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    @TableField(exist = false)
    private Category category;
}