package com.zedata.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Mr-Glacier
 * @apiNote Jwt配置类 : 用于返回Jwt的密钥以及存活时间
 */
@Component
public class JwtConfig {
    @Value("${my-jwt.secret}")
    private String secret;

    @Value("${my-jwt.expirationTime}")
    private long expirationTime;

    @Value("${my-jwt.aesKey}")
    private String aesKey;

    private static String staticSecret;
    private static long staticExpirationTime;

    private static String staticAesKey;

    @PostConstruct
    public void init() {
        staticSecret = this.secret;
        staticExpirationTime = this.expirationTime;
        staticAesKey = this.aesKey;
    }

    public static String getSecret() {
        return staticSecret;
    }

    public static long getExpirationTime() {
        return staticExpirationTime;
    }

    public static String getAesKey() {
        return staticAesKey;
    }
}
