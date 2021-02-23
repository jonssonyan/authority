package com.jonsson.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserVO extends PageVO implements Serializable {
    private static final long serialVersionUID = 7021223076084300328L;
    private Long id;
    private Long parentId; // 上级id
    private Long roleId; // 角色id
    @NotBlank(message = "{user.name.notBlank}")
    private String username;
    @NotBlank(message = "{user.password.notBlank}")
    private String password;
    @NotBlank(message = "{user.email.notBlank}")
    @Email(message = "{user.email.pattern}")
    private String email;
    private String qq;
    private String phone;
    private Integer state; // 状态 0/禁止 1/正常
    private Date createTime;
    private Date updateTime;
}