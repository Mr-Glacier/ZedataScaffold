package com.zedata.project.config;

import com.zedata.project.common.encryption.AesEncryption;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote JWT工具
 * @since 2025/1/20 5:53
 */
@Component
public class JwtUtil {

    @Value("${my-jwt.secret}")
    private String secret;

    @Value("${my-jwt.expirationTime}")
    private long expirationTime;

    @Value("${my-jwt.aesKey}")
    private String aesKey;

    /**
     * 生成Token
     *
     * @param account 账号
     * @return
     */
    public String generateToken(String account, Map<String, Object> claims) throws Exception {
        String sourceToken = Jwts.builder()
                .setSubject(account)
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JwtConfig.getExpirationTime()))
                .signWith(SignatureAlgorithm.HS256, JwtConfig.getSecret().getBytes())
                .compact();
        AesEncryption aesEncryption = new AesEncryption();
        return aesEncryption.encrypt(sourceToken,aesEncryption.decodeKeyFromString(JwtConfig.getAesKey()));
    }


    /**
     * 验证token的方法
     */
    public boolean validateToken(String token){
        try {
            // AES解密
            AesEncryption aesEncryption = new AesEncryption();
            token = aesEncryption.decrypt(token,aesEncryption.decodeKeyFromString(JwtConfig.getAesKey()));
            Jwts.parser().setSigningKey(JwtConfig.getSecret().getBytes()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从token中获取信息
     */
    public Claims getUsernameFromToken(String token){
        // AES解密
        try {
            AesEncryption aesEncryption = new AesEncryption();
            token = aesEncryption.decrypt(token,aesEncryption.decodeKeyFromString(JwtConfig.getAesKey()));
            return Jwts.parser()
                    .setSigningKey(JwtConfig.getSecret().getBytes())
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            throw new RuntimeException("token解析失败");
        }
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}