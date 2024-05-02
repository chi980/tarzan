package com.mjutarzan.tarzan.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


@Component
public class JwtTokenUtil {

    private Key key;

    public JwtTokenUtil(@Value("${spring.jwt.secret}")String secret) {


        byte[] byteSecretKey = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(byteSecretKey);
    }

    public String getUsername(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("username", String.class);
    }

    public String getRole(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("role", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    public String createJwt(String username, String role, Long expiredMs) {

        Claims claims = Jwts.claims();
        claims.put("username", username);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT Token 발급
//    public static String createToken(String loginId, String key, long expireTimeMs) {
//        // Claim = Jwt Token에 들어갈 정보
//        // Claim에 loginId를 넣어 줌으로써 나중에 loginId를 꺼낼 수 있음
//        Claims claims = Jwts.claims();
//        claims.put("loginId", loginId);
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
//                .signWith(SignatureAlgorithm.HS256, key)
//                .compact();
//    }
//
//    // Claims에서 loginId 꺼내기
//    public static String getLoginId(String token, String secretKey) {
//        return extractClaims(token, secretKey).get("loginId").toString();
//    }
//
//    // 밝급된 Token이 만료 시간이 지났는지 체크
//    public static boolean isExpired(String token, String secretKey) {
//        Date expiredDate = extractClaims(token, secretKey).getExpiration();
//        // Token의 만료 날짜가 지금보다 이전인지 check
//        return expiredDate.before(new Date());
//    }
//
//    // SecretKey를 사용해 Token Parsing
//    private static Claims extractClaims(String token, String secretKey) {
//        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
//    }
}
