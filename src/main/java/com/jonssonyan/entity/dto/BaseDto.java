package com.jonssonyan.entity.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class BaseDto implements Serializable {
    private static final long serialVersionUID = -6669951296126864566L;
    @Max(10)
    @Min(1)
    private Long pageSize = 10L;
    @Min(1)
    private Long pageNum = 1L;
    private Date startTime;
    private Date endTime;
    private List<Long> ids;
}