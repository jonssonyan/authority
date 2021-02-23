package com.jonsson.security.utils;

import com.jonsson.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class SecurityUtil {
    /**
     * 获取当前登录用户
     *
     * @return
     */
    public static User getCurrentUser() {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            throw new RuntimeException("Log current user error: UnAuthenticated subject");
        }
        return (User) subject.getPrincipal();
    }
}