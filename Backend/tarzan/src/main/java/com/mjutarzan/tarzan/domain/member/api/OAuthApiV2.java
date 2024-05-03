package com.mjutarzan.tarzan.domain.member.api;

import com.mjutarzan.tarzan.domain.member.model.vo.Role;
import com.mjutarzan.tarzan.global.security.jwt.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OAuthApiV2 {
    private final JwtTokenUtil jwtTokenUtil;

    @Value("${spring.jwt.access-token-validity-in-seconds}")
    private Long accessTokenValidTime;

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        //get refresh token
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("refresh")) {

                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {

            //response status code
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        //expired check
        try {
            jwtTokenUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {

            //response status code
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtTokenUtil.getCategory(refresh);

        if (!category.equals("refresh")) {

            //response status code
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String username = jwtTokenUtil.getUsername(refresh);
        String roleString = jwtTokenUtil.getRole(refresh);
        Role role = Role.valueOf(roleString);

        //make new JWT
        String newAccessToken = jwtTokenUtil.createAccessToken(username, role);
        String newRefreshToken = jwtTokenUtil.createRefreshToken(username, role);

        //response
        response.setHeader("access", newAccessToken);
        response.addCookie(jwtTokenUtil.createCookie("Authorization", newAccessToken));
        response.addCookie(jwtTokenUtil.createCookie("refresh", newRefreshToken));

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
