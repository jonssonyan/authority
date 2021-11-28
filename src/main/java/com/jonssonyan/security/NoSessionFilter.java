package com.jonssonyan.security;

import cn.hutool.core.util.StrUtil;
import com.jonssonyan.security.constant.SystemConstant;
import com.jonssonyan.security.entity.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Slf4j
public class NoSessionFilter extends BasicHttpAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        // 1.从Cookie获取token
        String token = getTokenFromCookie(servletRequest);
        if (StrUtil.isBlank(token)) {
            // 2.从headers中获取
            token = servletRequest.getHeader(SystemConstant.TOKEN_HEADER);
        }
        if (StrUtil.isBlank(token)) {
            // 3.从请求参数获取
            token = request.getParameter(SystemConstant.TOKEN_HEADER);
        }
        if (StrUtil.isBlank(token)) {
            return false;
        }
        // 验证token
        JwtToken jwtToken = new JwtToken(token);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        // todo https://www.cnblogs.com/red-star/p/12121941.html https://blog.csdn.net/qq_43721032/article/details/110188342
        try {
            SecurityUtils.getSubject().login(jwtToken);
        } catch (Exception e) {
            return false;
        }
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        PrintWriter printWriter = response.getWriter();
        response.setCharacterEncoding("utf-8");
        printWriter.write("403");
        printWriter.flush();
        printWriter.close();
        return false;
    }

    private String getTokenFromCookie(HttpServletRequest request) {
        String token = null;
        Cookie[] cookies = request.getCookies();
        int len = null == cookies ? 0 : cookies.length;
        if (len > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(SystemConstant.TOKEN_HEADER)) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        return token;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
