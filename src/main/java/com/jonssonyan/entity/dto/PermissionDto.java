package com.jonssonyan.entity.dto;

import com.jonssonyan.entity.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class PermissionDto extends BaseDto implements Serializable {
    private static final long serialVersionUID = -2797154122372472654L;
    private Long id;
    private String name;
    private String description;
    private Integer state;
    private Date createTime;
    private Date updateTime;
}