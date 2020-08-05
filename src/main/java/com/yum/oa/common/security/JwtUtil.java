package com.yum.oa.common.security;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @description
 * @author: gaoyu
 * @create: 2020-08-04
 * @version: 0.0.1
 **/
@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expire}")
    private long expire;
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    public JwtUser getUserFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return JSON.parseObject(JSON.toJSONString(claims), JwtUser.class);
    }

    public String generateAccessToken(JwtUser user) {
        return generateToken(user);
    }

    public boolean validateToken(String token, JwtUser user) {
        Claims claims = getClaimsFromToken(token);
        return user.getUserId() == Long.parseLong(claims.get("userId").toString())
                && user.getUsername().equals(claims.get("userName"))
                && isTokenExpired(token);
    }

    public String refreshAccessToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return generateToken(claims, expire);
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private String generateToken(JwtUser user) {
        return generateToken(JSON.parseObject(JSON.toJSONString(user)), expire);
    }

    private String generateToken(Map<String, Object> claims, long expire) {
        return Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(generateExpireTime(expire))
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
    }

    private Date generateExpireTime(long expire) {
        return new Date(System.currentTimeMillis() + (expire * 1000));
    }

    private boolean isTokenExpired(String token) {
        return getExpiredTimeOfToken(token).before(new Date());
    }

    private Date getExpiredTimeOfToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }
}
