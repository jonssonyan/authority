package com.jonssonyan.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleVo extends BaseVo implements Serializable {
    private static final long serialVersionUID = 2147982144935437262L;
    private Long id;
    private String parentId;
    private String name;
    private Integer state;
    private Date createTime;
    private Date updateTime;
}