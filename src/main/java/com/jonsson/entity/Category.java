package com.jonsson.entity;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("`category`")
public class Category implements Serializable {
    private static final long serialVersionUID = -5888981197198625157L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long parentId;
    private String path;
    private Integer level;
    private String name;
    private Long creator;
    private Integer state; // 状态 0/下架 1/上架 默认上架
    private Date createTime;
    private Date updateTime;

    @TableField(exist = false)
    private List<Category> Categorys;

    public final void parent(Category category) {
        if (category != null) {
            this.parentId = category.getId();
            this.path = (category.getPath() == null ? "" : category.getPath()) + category.getId() + "-";
            this.level = StrUtil.count(this.path, "-");
        } else {
            this.parentId = null;
            this.path = null;
            this.level = null;
        }
    }
}