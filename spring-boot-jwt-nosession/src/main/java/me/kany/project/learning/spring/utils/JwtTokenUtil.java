/**
 * Project Name:learning-spring
 * File Name:JwtTokenUtil.java
 * Package Name:me.kany.project.learning.spring.utils
 * Date:2020年03月13日 10:47
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.utils;

import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:JwtTokenUtil<br/>
 * Function: jwt的工具类<br/>
 * Date:2020年03月13日 10:47<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */

@Data

@Component
@ConfigurationProperties(prefix = JwtTokenUtil.JWT_PREFIX)
@Log4j2
public class JwtTokenUtil {

    public static final String JWT_PREFIX = "jwt";

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    private String secret = "dsfa&*)#@)3&&FTFRGHYGGYHMNBFnmxvb)V)(DS))(*FDS9082139889fds7v&78df8732";
    private Long expiration = 1000 * 24 * 60 * 60L;
    private String tokenPrefix = "Bearer ";
    private String authHead = "Authorization";


    /**
     * generateFinalKey: 生成静态Key<br/>
     *
     * @return
     * @author Jason.Wang
     * @createTime 2020/3/13 11:49
     */
    private Key generateFinalKey() {
        //根据指定字符串生成Key，相同字符串生成的Key也相同的，这个字符串至少要有256bit长，推荐长一些，生成的密钥也会变长。
        //推荐这种做法，每次都会生成同样的一串Key来使用
        byte[] bytes = secret.getBytes();
        //生成SHA密钥
        Key key = Keys.hmacShaKeyFor(bytes);
        return key;
    }

    /***
     * @creed: Talk is cheap,show me the code
     * @Date: 2019/12/26 0026 16:37
     * @Description: 根据负载生成JWT中的token
     * @Param claims:
     * @Return:
     **/
    private String generateToken(Map<String, Object> claims) {
//        iss: jwt签发者
//        sub: jwt所面向的用户
//        aud: 接收jwt的一方
//        exp: jwt的过期时间，这个过期时间必须要大于签发时间
//        nbf: 定义在什么时间之前，该jwt都是不可用的.
//        iat: jwt的签发时间
//        jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(generateFinalKey())
                //创建时间
                .setIssuedAt(new Date())
                .compact();
    }

    /**
     * 从token中获取JWT中的负载
     *
     * @param token
     * @return
     */
    private Jws<Claims> getClaimsFromToken(String token) {
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parserBuilder().setSigningKey(generateFinalKey()).build().parseClaimsJws(token);
        } catch (Exception e) {
            log.info("JWT格式验证失败:{}", token);
        }
        return claims;
    }

    /**
     * 生成token的过期时间
     *
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(Instant.now().toEpochMilli() + expiration * 1000);
    }

    /**
     * 从token中获取登录用户名
     *
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token) {
        String username = null;
        try {
            Jws<Claims> claims = getClaimsFromToken(token);
            username = claims.getBody().getSubject();
        } catch (Exception e) {
        }
        return username;
    }

    /**
     * 验证token是否有效
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     * @return
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 验证token是否有效
     *
     * @param token    客户端传入的token
     * @param userName 从数据库中查询出来的用户信息
     * @return
     */
    public boolean validateToken(String token, String userName) {
        String username = getUserNameFromToken(token);
        return username.equals(userName) && !isTokenExpired(token);
    }

    /**
     * 判断token是否已失效
     *
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取失效时间
     *
     * @param token
     * @return
     */
    public Date getExpiredDateFromToken(String token) {
        Jws<Claims> claims = getClaimsFromToken(token);
        return claims.getBody().getExpiration();
    }

    /**
     * 根据用户信息生成token
     *
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * generateToken: 创建Token<br/>
     *
     * @param userName
     * @return
     * @author Jason.Wang
     * @createTime 2020/3/13 12:20
     */
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Claims.SUBJECT, userName);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 刷新未过期的token信息
     *
     * @param token 带token header的token信息
     * @return
     */
    public String refreshHeadToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        //token校验不通过
        Jws<Claims> claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }
        //如果token已经过期，不支持刷新
        if (isTokenExpired(token)) {
            return null;
        }
        //如果token在30分钟之内刚刷新过，返回原token
        if (tokenRefreshJustBefore(token, 30 * 60)) {
            return token;
        } else {
            claims.getBody().setNotBefore(new Date());
            return generateToken(claims.getBody());
        }
    }

    /**
     * 判断token是否在指定的时间内刚刚刷新过
     *
     * @param token 原token
     * @param time  指定时间(s)
     * @return
     */
    private boolean tokenRefreshJustBefore(String token, int time) {
        Jws<Claims> claims = getClaimsFromToken(token);
        Date created = claims.getBody().get(CLAIM_KEY_CREATED, Date.class);
        Date refreshDate = new Date();
        //刷新时间在创建时间的指定时间内
        if (refreshDate.after(created) && refreshDate.before(DateUtil.offsetSecond(created, time))) {
            return true;
        }
        return false;
    }
}
