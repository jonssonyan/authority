package com.jonssonyan.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthorizedUser implements Serializable {
    private static final long serialVersionUID = -8152270249992675066L;
    private Long id;
    private String username;
    private Long roleId;
}