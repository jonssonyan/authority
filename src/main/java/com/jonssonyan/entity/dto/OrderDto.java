package com.jonssonyan.entity.dto;

import com.jonssonyan.entity.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderDto extends BaseDto implements Serializable {
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