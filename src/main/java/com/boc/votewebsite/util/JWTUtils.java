package com.boc.votewebsite.util;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;

import static cn.hutool.crypto.SecureUtil.md5;

public class JWTUtils {
    public static String generateToken(String userID){
        String sign = md5("siwangchongfengxiaozu");
        return JWT.create().withAudience(userID) //将userID作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),2)) //两小时后过期
                .sign(Algorithm.HMAC256(sign)); //用用户密码作为签名
    }

    public static DecodedJWT verify(String token) {
        String sign = md5("siwangchongfengxiaozu");
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(sign)).build();
        return jwtVerifier.verify(token);
    }
}
