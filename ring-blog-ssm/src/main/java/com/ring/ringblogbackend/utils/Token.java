package com.ring.ringblogbackend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.Objects;

// 生成&解析token
public class Token {

    public String genToken(String payloads) {
        return JWT.create()
                .withClaim("user", payloads)
                .withExpiresAt(new Date(System.currentTimeMillis() + 28800000 * 7))
                .sign(Algorithm.HMAC256("ring"));
    }

    public String parseToken(String token) {
        try {
            if (!Objects.equals(token, "")) {
                JWTVerifier verifier = JWT.require(Algorithm.HMAC256("ring")).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                Claim claim = decodedJWT.getClaim("user");
                return claim.asString();
            }
            else {
                return "Token已经过期";
            }
        } catch (TokenExpiredException e) {
            return "Token已经过期";
        }
    }
}
