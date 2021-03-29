package com.jonsson.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductVO extends BaseVO implements Serializable {
    private static final long serialVersionUID = -4492232772022212494L;
    private Long id;
    private Long categoryId;
    private String name;
    private BigDecimal price;
    private Integer state;
    private Date createTime;
    private Date updateTime;
}