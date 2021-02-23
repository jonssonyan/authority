package com.jonsson.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

@Data
@AllArgsConstructor
public class JwtToken implements AuthenticationToken {
    private static final long serialVersionUID = 3768999689537588845L;
    private String token;

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
