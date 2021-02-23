package com.jonsson.entity.vo;

import lombok.Data;

@Data
public class AuthorizedUser {
    private Long id;
    private String username;
    private Long roleId;
}