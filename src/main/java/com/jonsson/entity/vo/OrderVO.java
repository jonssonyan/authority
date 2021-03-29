package com.jonsson.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderVO extends BaseVO implements Serializable {
    private static final long serialVersionUID = -3640807116600926284L;
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
}