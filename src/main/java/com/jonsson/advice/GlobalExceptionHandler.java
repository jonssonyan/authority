package com.jonsson.advice;

import com.jonsson.entity.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<Object> exception(HttpServletRequest req, Exception e) {
        log.error("---exception Handler---Host {} invokes url {} ERROR: ", req.getRemoteHost(), req.getRequestURL(), e);
        return Result.fail("系统错误，请联系网站管理员！");
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public Result<Object> runtimeException(HttpServletRequest req, RuntimeException e) {
        log.error("---runtimeException Handler---Host {} invokes url {} ERROR: ", req.getRemoteHost(), req.getRequestURL(), e);
        return Result.fail(e.getMessage());
    }

    /**
     * 处理Shiro权限拦截异常
     */
    @ExceptionHandler(value = {AuthorizationException.class, UnauthorizedException.class})
    @ResponseBody
    public Result<Object> authorizationException(HttpServletRequest req, UnauthorizedException e) {
        log.error("---authorizationException Handler---Host {} invokes url {} ERROR: ", req.getRemoteHost(), req.getRequestURL(), e);
        return Result.fail("权限不足");
    }
}