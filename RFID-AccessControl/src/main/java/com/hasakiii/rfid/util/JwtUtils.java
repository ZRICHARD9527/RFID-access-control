package com.hasakiii.rfid.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

public class JwtUtils {

    public static final long EXPIRE = 1000 * 60 * 60 * 24 * 10; // token过期时间  7*12小时
    public static final String APP_SECRET = "cmZpZOmXqOemgeeuoeeQhg=="; // 加密的密钥

    // 生成token字符串
    public static String getToken(Map<String, String> map) {
        //创建jwt builder
        JWTCreator.Builder builder = JWT.create();
        //循环添加负载
        map.forEach(builder::withClaim);
        String token = builder.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE))//过期时间
                .sign(Algorithm.HMAC256(APP_SECRET));//签名
        return token;
    }

    // 判断token的合法性、有效期等进行判断，直接对token进行判断
    // 注意这里的异常直接交给拦截器中去处理
    public static boolean checkToken(String token) {
        if (!StringUtils.hasLength(token)) return false;
        try {
            JWT.require(Algorithm.HMAC256(APP_SECRET)).build().verify(token);
        } catch (Exception e) {
            throw e; // 抛出异常交给拦截器处理
        }
        return true;
    }

    // 判断token是否存在与有效，从请求头中获取token
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if (!StringUtils.hasLength(jwtToken)) return false;
            JWT.require(Algorithm.HMAC256(APP_SECRET)).build().verify(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 根据token获取用户信息
    public static DecodedJWT getTokenInfo(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(APP_SECRET)).build().verify(token);
        return decodedJWT;
    }

    // 根据token获取用户信息
    public static DecodedJWT getTokenInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (!StringUtils.hasLength(token)) return null;
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(APP_SECRET)).build().verify(token);
        return decodedJWT;
    }

}
