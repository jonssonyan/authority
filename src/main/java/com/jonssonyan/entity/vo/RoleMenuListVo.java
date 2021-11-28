package com.jonssonyan.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleMenuListVo extends BaseVo implements Serializable {
    private static final long serialVersionUID = 2310103009618403876L;
    private Long id;
    private Long roleId;
    private Long MenuListId;
    private Integer state;
    private Date startTime;
    private Date endTime;
}
