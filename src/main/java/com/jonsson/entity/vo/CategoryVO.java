package com.jonsson.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryVO extends BaseVO implements Serializable {
    private static final long serialVersionUID = 4432159672935772196L;
    private Long id;
    private String name;
    private Long parentId;
    private Long creator;
    private Integer state;
    private Date createTime;
    private Date updateTime;
}