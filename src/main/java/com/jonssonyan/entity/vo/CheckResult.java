package com.jonssonyan.entity.vo;

import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class CheckResult implements Serializable {
    private static final long serialVersionUID = -6754475697804532751L;
    private boolean success;
    private Claims claims;
    private String errCode;
}
