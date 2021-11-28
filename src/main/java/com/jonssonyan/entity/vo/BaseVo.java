package com.jonssonyan.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;
import java.util.List;

@Data
public class BaseVo {
    @NotNull
    private Long pageSize = 10L;
    @NotNull
    private Long pageNum = 1L;
    @Past
    private Date startTime;
    @Past
    private Date endTime;

    private List<Long> ids;
}