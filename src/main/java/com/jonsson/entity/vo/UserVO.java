package com.jonsson.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserVO extends BaseVO implements Serializable {
    private static final long serialVersionUID = 7021223076084300328L;
    private Long id;
    private Long parentId; // 上级id
    private Long roleId; // 角色id
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "用户名不能为空")
    private String password;
    @NotBlank(message = "用户名不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;
    private String qq;
    private String phone;
    private Integer state; // 状态 0/禁止 1/正常
    private Date createTime;
    private Date updateTime;
}