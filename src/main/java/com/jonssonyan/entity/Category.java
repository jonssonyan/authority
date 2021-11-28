package com.jonssonyan.entity;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("`category`")
@ApiModel("分类")
public class Category extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -5888981197198625157L;

    @ApiModelProperty("父级id")
    private Long parentId;
    @TableField("`path`")
    @ApiModelProperty("路径")
    private String path;
    @ApiModelProperty("等级")
    private Integer level;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("创建人")
    private Long creator;
    @ApiModelProperty("状态 0/下架 1/上架 默认上架")
    private Integer state; // 状态 0/下架 1/上架 默认上架

    @TableField(exist = false)
    private List<Category> Categorys;

    public final void parent(@NotNull Category category) {
        this.parentId = category.getId();
        this.path = (category.getPath() == null ? "" : category.getPath()) + category.getId() + "-";
        this.level = StrUtil.count(this.path, "-");
    }
}