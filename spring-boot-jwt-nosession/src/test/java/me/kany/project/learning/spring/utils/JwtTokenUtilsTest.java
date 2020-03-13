/**
 * Project Name:learning-spring
 * File Name:JwtTokenUtilsTest.java
 * Package Name:me.kany.project.learning.spring.utils
 * Date:2020年03月13日 10:59
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.utils;

import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;

/**
 * ClassName:JwtTokenUtilsTest<br/>
 * Function: JwtTokenUtils测试类<br/>
 * Date:2020年03月13日 10:59<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Log4j2
public class JwtTokenUtilsTest {
//    private String secret = "dsfa&*)#@)908v9109)V)(DS))(*FDS9082139889fds7v&78df8732";
//    private Long expiration = 24 * 60 * 60L;
//    private String tokenPrefix = "Bearer ";
//    private String authHead = "Authorization";
//
//
//    private Key finalKey;
//    private String testToken = "eyJzYW5lciI6Imd1Z3VnIiwiYWxnIjoiSFMzODQifQ.eyJpc3MiOiJjYy5jb255bGkiLCJzdWIiOiJ1c2VybmFtZSIsInNhbmVyIjoiZ3VndWd1Z3UifQ.eMJvbpNY67gddeyJI_DcjzwpmH7TLRmuViRS5-XJZ-z90nN0_XQFEdpxmbfnaKyg";
//
//    /**
//     * testJwtRandomKey: 测试Jwt的随机Key<br/>
//     *
//     * @author Jason.Wang
//     * @createTime 2020/3/13 11:03
//     */
//    @Test
//    public void testJwtRandomKey() {
//        //随机生成Key
//        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
//        log.debug(key.toString());
//        //用Base64解码可以获取Key对应的字符串
//        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
//        log.debug(encodedKey);
//    }
//
//    /**
//     * testJwtFinalKey: 测试Jwt的固定Key<br/>
//     *
//     * @author Jason.Wang
//     * @createTime 2020/3/13 11:03
//     */
//    @BeforeTest
//    public void testJwtFinalKey() {
//        //根据指定字符串生成Key，相同字符串生成的Key也相同的，这个字符串至少要有256bit长，推荐长一些，生成的密钥也会变长。
//        //推荐这种做法，每次都会生成同样的一串Key来使用
//        byte[] bytes = secret.getBytes();
//        //生成SHA密钥
//        Key key = Keys.hmacShaKeyFor(bytes);
//        log.debug(key.toString());
//        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
//        log.debug(encodedKey);
//
//        finalKey = key;
//    }
//
//    @BeforeTest
//    public void testJwtTokenGenerate() {
//        //生成JWT
//        String token = Jwts.builder()
////                .setHeaderParam("saner", "gugug")
//                .setIssuer("cc.conyli")
//                .setSubject("username")
//                .setExpiration(new Date(Instant.now().toEpochMilli() + expiration * 1000))
//                .setNotBefore(new Date())
//                .claim("saner", "gugugugu")
//                .signWith(finalKey)
//                .compact();
//        testToken = token;
//        log.debug("生成Token为：{}", testToken);
//    }
//
//    @Test
//    public void testJwtTokenParser() {
//        //对应上边的1-3步，解析JWT
//        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(finalKey).build().parseClaimsJws(testToken);
//        //得到的是一个Jws<Claims>对象
//        // getBody()和.getHeader()得到的都是生成JWT时候传入的泛型，其实就可以当成Map<String,Object>
//        log.debug(claims.getBody());
//        log.debug(claims.getHeader());
//        //取出Registered claim有特殊的方法
//        log.debug("getIssuer：{}", claims.getBody().getIssuer());
//        log.debug("getSubject：{}", claims.getBody().getSubject());
//        log.debug("getExpiration：{}", claims.getBody().getExpiration());
//        log.debug("getId：{}", claims.getBody().getId());
//        log.debug("getNotBefore：{}", claims.getBody().getNotBefore());
//        //取出自定义的键值就用.get()方法
//        log.debug("Body 获取 saner：{}", claims.getBody().get("saner"));
//        log.debug("Header 获取 saner：{}", claims.getHeader().get("saner"));
//    }

    @Test
    public void testMethod() {
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        String token = jwtTokenUtil.generateToken("1000000000");
        log.debug("生成Token：{}", token);
        log.debug("是否有效：{}", jwtTokenUtil.validateToken(token, "1000000000"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.debug("有效时间：{}", simpleDateFormat.format(jwtTokenUtil.getExpiredDateFromToken(token)));

        token = jwtTokenUtil.refreshHeadToken(token);
        log.debug("产生新的Token：{}", token);
        log.debug("有效时间：{}", simpleDateFormat.format(jwtTokenUtil.getExpiredDateFromToken(token)));
    }
}
