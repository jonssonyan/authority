package com.jonsson.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class MenuListVO extends PageVO implements Serializable {
    private static final long serialVersionUID = 3086526136023139210L;
    private Long id;
    private Long roleId;
    private Long parentId;
    private String name;
    private String icon;
    private Integer priority;
    private String path;
    private Integer state;
    private Date createTime;
    private Date updateTime;
}
