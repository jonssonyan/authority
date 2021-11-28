package com.jonssonyan.security.config;

import com.jonssonyan.security.JWTRealm;
import com.jonssonyan.security.NoSessionFilter;
import com.jonssonyan.security.StatelessDefaultSubjectFactory;
import com.jonssonyan.security.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


    /**
     * Shiro生命周期处理器
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * DefaultAdvisorAutoProxyCreator的顺序必须在shiroFilterFactoryBean之前，不然SecurityUtils.getSubject().getPrincipal()获取不到参数
     *
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultSecurityManager());
        return authorizationAttributeSourceAdvisor;
    }

    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultSecurityManager());

        // 过滤规则
        Map<String, String> linkedHashMap = new LinkedHashMap<>();
        // 无状态登录情况下关闭了shiro中的session，导致所有需要加上authc接口请求时候都会报错，
        // 所以使用@RequiresRoles,@RequiresPermissions注解,aop方式实现接口的权限校验

        /* 添加shiro的内置过滤器，自定义url规则
         * Shiro自带拦截器配置规则
         * rest：比如/admins/user/**=rest[user],根据请求的方法，相当于/admins/user/**=perms[user：method] ,其中method为post，get，delete等
         * port：比如/admins/user/**=port[8081],当请求的url的端口不是8081是跳转到schemal：//serverName：8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString是你访问的url里的？后面的参数
         * perms：比如/admins/user/**=perms[user：add：*],perms参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，比如/admins/user/**=perms["user：add：*,user：modify：*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法
         * roles：比如/admins/user/**=roles[admin],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，比如/admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。//要实现or的效果看http://zgzty.blog.163.com/blog/static/83831226201302983358670/
         * anon：比如/admins/**=anon 没有参数，表示可以匿名使用
         * authc：比如/admins/user/**=authc表示需要认证才能使用，没有参数
         * authcBasic：比如/admins/user/**=authcBasic没有参数表示httpBasic认证
         * ssl：比如/admins/user/**=ssl没有参数，表示安全的url请求，协议为https
         * user：比如/admins/user/**=user没有参数表示必须存在用户，当登入操作时不做检查
         * 详情见文档 http://shiro.apache.org/web.html#urls-
         * */
        // 用户权限
//        linkedHashMap.put("/api/user/selectPage", "perms[user:select]");
//        linkedHashMap.put("/api/user/selectById", "perms[user:select]");
//        linkedHashMap.put("/api/user/updateById", "perms[user:update]");
//        linkedHashMap.put("/api/user/removeByIds", "perms[user:delete]");
//        // 商品权限
//        linkedHashMap.put("/api/product/deleteBatchIds", "perms[product:delete]");
//        linkedHashMap.put("/api/product/updateById", "perms[product:update]");
//        linkedHashMap.put("/api/product/insert", "perms[product:add]");
//
//        // 角色权限
//        linkedHashMap.put("/api/role/saveOrUpdate", "perms[role:add,role:update]");
//        linkedHashMap.put("/api/role/removeByIds", "perms[role:delete]");
//        linkedHashMap.put("/api/role/getById", "perms[role:select]");
//        linkedHashMap.put("/api/role/selectPage", "perms[role:select]");
//
//        // 菜单权限
//        linkedHashMap.put("/api/menuList/removeByIds", "perms[menuList:delete]");
//        linkedHashMap.put("/api/menuList/saveOrUpdate", "perms[menuList:add,menuList:update]");
//
//        // 订单权限
//        linkedHashMap.put("/api/order/deleteBatchIds", "perms[order:delete]");
//        linkedHashMap.put("/api/order/updateById", "perms[order:update]");
//
//        // 授权的权限
//        linkedHashMap.put("/api/rolePermission/saveOrUpdate", "perms[rolePermission:add]");
//        linkedHashMap.put("/api/rolePermission/removeByIds", "perms[rolePermission:delete]");

        // 自定义过滤器
        HashMap<String, Filter> filterHashMap = new HashMap<>();
        filterHashMap.put("jwt", new NoSessionFilter());
        shiroFilterFactoryBean.setFilters(filterHashMap);
        // 登录之后才可以请求的接口
        linkedHashMap.put("/api/**", "jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(linkedHashMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager defaultSecurityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealms(Arrays.asList(userRealm(), jwtRealm()));
        // 禁用shiro中的session
        DefaultSubjectDAO defaultSubjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        defaultSubjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        defaultWebSecurityManager.setSubjectDAO(defaultSubjectDAO);
        defaultWebSecurityManager.setSubjectFactory(subjectFactory());
        return defaultWebSecurityManager;
    }

    /**
     * 登录的认证和授权
     *
     * @return
     */
    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return userRealm;
    }

    /**
     * token的认证和授权
     *
     * @return
     */
    @Bean
    public JWTRealm jwtRealm() {
        return new JWTRealm();
    }

    @Bean
    public StatelessDefaultSubjectFactory subjectFactory() {
        return new StatelessDefaultSubjectFactory();
    }

    /*
     * 凭证匹配器 由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");// 散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(1024);// 散列的次数，比如散列两次，相当于MD5(MD5(""));
        return hashedCredentialsMatcher;
    }

    @Bean
    public CookieRememberMeManager cookieRememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setMaxAge(259200000);
        cookieRememberMeManager.setCookie(simpleCookie);
        cookieRememberMeManager.setCipherKey(Base64.decode("6ZmI6I2j5Y+R5aSn5ZOlAA=="));
        return cookieRememberMeManager;
    }
}