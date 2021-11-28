package com.jonssonyan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("`order`")
@ApiModel("订单")
public class Order extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -6692823745412833806L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long productId;
    private Long creator;
    private Long quantity;
    private String subject;
    private String address;
    private String outTradeNo;
    private String totalAmount;
    private Integer state;
    private Date createTime;
    private Date updateTime;
    @TableField(exist = false)
    private Product product;
}