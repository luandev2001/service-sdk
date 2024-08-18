package com.xuanluan.mc.sdk.utils;

import com.xuanluan.mc.sdk.exception.MessageSourceException;
import io.jsonwebtoken.*;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

import static com.xuanluan.mc.sdk.service.constant.BaseConstant.ResponseCode;

public class JwtUtils {
    public static Claims decode(String token, PublicKey publicKey) {
        try {
            return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException exception) {
            throw new MessageSourceException(ResponseCode.EXPIRED, "token.expired", "");
        } catch (IllegalArgumentException exception) {
            throw new MessageSourceException(ResponseCode.INVALID, "token.invalid", "");
        }
    }

    public static JwtBuilder encode(Claims claims, long expirationTime, PrivateKey privateKey) {
        Date currentDate = new Date();
        Date expirationDay = new Date(currentDate.getTime() + expirationTime * 1000); // expirationTime * 1000 = milliseconds
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(currentDate)
                .setExpiration(expirationDay)
                .signWith(SignatureAlgorithm.RS256, privateKey);
    }
}
