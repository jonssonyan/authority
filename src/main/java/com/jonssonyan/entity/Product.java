package com.jonssonyan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("`product`")
@ApiModel("商品")
public class Product  extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -54169886181194401L;
    @ApiModelProperty("分类id")
    private Long categoryId;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("价格")
    private BigDecimal price;
    @ApiModelProperty("创建人")
    private Long creator;
    @ApiModelProperty("状态")
    private Integer state;

    @TableField(exist = false)
    private Category category;
}