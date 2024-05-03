package com.mjutarzan.tarzan.global.security.jwt;

import com.mjutarzan.tarzan.domain.member.model.vo.Role;
import com.mjutarzan.tarzan.global.security.token.RefreshToken;
import com.mjutarzan.tarzan.global.security.token.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


@Component
public class JwtTokenUtil {

    private Key key;

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${spring.jwt.access-token-validity-in-seconds}")
    private Long accessTokenValidTime;

    @Value("${spring.jwt.refresh-token-validity-in-seconds}")
    private Long refreshTokenValidTime;

    public JwtTokenUtil(@Value("${spring.jwt.secret}")String secret, RefreshTokenRepository refreshTokenRepository) {


        byte[] byteSecretKey = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(byteSecretKey);

        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("username", String.class);
    }

    public String getRole(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("role", String.class);
    }

    public String getCategory(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("category", String.class);
    }

    /**
     * token이 만료되었는지 확인
     * @param token
     * @return
     */
    public Boolean isExpired(String token){

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    public String createJwt(String username, Role role, String category, Long expiredMs) {

        Claims claims = Jwts.claims();
        claims.put("username", username);
        claims.put("role", role);
        claims.put("category", category);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createAccessToken(String username, Role role) {
        return createJwt(username, role, "access",accessTokenValidTime);
    }

    public String createRefreshToken(String username, Role role) {

        String refreshTokenString = createJwt(username, role, "refresh", refreshTokenValidTime);
        RefreshToken refreshToken = new RefreshToken(refreshTokenString, username, refreshTokenValidTime);
        refreshTokenRepository.save(refreshToken);

        return refreshTokenString;
    }

    public Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(accessTokenValidTime.intValue());
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }


}
