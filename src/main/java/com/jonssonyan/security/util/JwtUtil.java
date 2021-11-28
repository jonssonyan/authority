package com.jonssonyan.security.util;

import com.jonssonyan.security.constant.SystemConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;

@Data
@Component
@Slf4j
public class JwtUtil {
    /**
     * 生成足够的安全随机密钥，以适合符合规范的签名
     */
    private static byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SystemConstant.JWT_SECRET_KEY);
    private static SecretKey secretKey = Keys.hmacShaKeyFor(apiKeySecretBytes);

    /**
     * 创建JWT
     *
     * @param subject      主体，用户名
     * @param isRememberMe 记住我
     * @return
     */
    public static String createToken(String subject, boolean isRememberMe) {
        long expiration = isRememberMe ? SystemConstant.EXPIRATION_REMEMBER : SystemConstant.EXPIRATION;

        String tokenPrefix = Jwts.builder()
                .setHeaderParam("typ", SystemConstant.TOKEN_TYPE)
                .signWith(secretKey, SignatureAlgorithm.HS256) // 设置加密方式
                .setIssuer("Authority") // //签发的人
                .setIssuedAt(new Date()) // 签发时间
                .setSubject(subject) // 主体
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000)) // 有效时间
                .compact();
        return tokenPrefix;
    }

    public static boolean isTokenExpired(String token) {
        Date expiredDate = getTokenBody(token).getExpiration();
        return expiredDate.before(new Date());
    }

    public static String getUsernameByToken(String token) {
        return getTokenBody(token).getSubject();
    }

    /**
     * 解析JWT
     *
     * @param token
     * @return
     */
    private static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}