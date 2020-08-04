package com.yum.oa.common.security;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.UUID;

/**
 * @description
 * @author: gaoyu
 * @create: 2020-08-04
 * @version: v2.0
 **/
public class JwtUtil {
    @Value("${jwt.secret}")
    private static String secret;
    @Value("${jwt.expire}")
    private static long expire;
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    public static JwtUser getUserFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return JSON.parseObject(JSON.toJSONString(claims), JwtUser.class);
    }

    public static String generateAccessToken(JwtUser user) {
        return generateToken(user);
    }

    public static boolean validateToken(String token, JwtUser user) {
        Claims claims = getClaimsFromToken(token);
        return user.getUserId() == claims.get("userId")
                && user.getUsername().equals(claims.get("userName"))
                && isTokenExpired(token);
    }

    public static String refreshAccessToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return generateToken(claims, expire);
    }

    private static Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private static String generateToken(JwtUser user) {
        return generateToken(JSON.parseObject(JSON.toJSONString(user), Claims.class), expire);
    }

    private static String generateToken(Claims claims, long expire) {
        return Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(generateExpireTime(expire))
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
    }

    private static Date generateExpireTime(long expire) {
        return new Date(System.currentTimeMillis() + (expire * 1000));
    }

    private static boolean isTokenExpired(String token) {
        return getExpiredTimeOfToken(token).before(new Date());
    }

    private static Date getExpiredTimeOfToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }
}
