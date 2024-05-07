package com.mjutarzan.tarzan.global.security.jwt;

import com.mjutarzan.tarzan.domain.member.entity.CustomOAuth2User;
import com.mjutarzan.tarzan.domain.member.model.dto.UserDTO;
import com.mjutarzan.tarzan.domain.member.model.vo.Role;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 위에서 언급한 바와 같이 사용자의 요청에서 Jwt Token을 추출해 통과하면 권한을 부여하고 실패하면 권한을 부여하지 않고 다음 필터로 진행시킴
 */
@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        필터를 거치지 않을 URL 패턴일경우, 필터를 건너 뛰게 됩니다.
        return request.getRequestURI().contains("token/");
    }

//     access token 값이 비어있을 때 더 이상 검증하지 않고 다음 filter로 진행
//    permitAll( )로 설정을 해주더라도, JwtAuthFilter는 매 요청마다 인증을 수행하기 떄문
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Header에서 accessToken을 꺼냄
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorizationHeader:{}", authorizationHeader );

        // 토큰 검사 생략(모두 허용 URL의 경우 토큰 검사 통과)
        if(!StringUtils.hasText(authorizationHeader)){
            doFilter(request, response, filterChain);
            return;
        }

        // Header의 Authorization 값이 비어있으면 => Jwt Token을 전송하지 않음 => 로그인 하지 않음
        if(authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Header의 Authorization 값이 'Bearer '로 시작하지 않으면 => 잘못된 토큰
        if(!authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 전송받은 값에서 'Bearer ' 뒷부분(Jwt Token) 추출
        String token = authorizationHeader.split(" ")[1];


        // AccessToken을 검증하고, 만료되었을경우 예외를 발생시킨다.
        if (jwtUtil.isExpired(token)) {
            log.error("access token expired!");
            throw new JwtException("Access Token Expired");
        }


        //cookie들을 불러온 뒤 Authorization Key에 담긴 쿠키를 찾음
//        String authorization = null;
//        String refresh = null;
//        Cookie[] cookies = request.getCookies();
//        for (Cookie cookie : cookies) {
//
//            System.out.println(cookie.getName());
//            if (cookie.getName().equals("Authorization")) {
//
//                authorization = cookie.getValue();
//            }else if (cookie.getName().equals("refresh")) {
//
//                refresh = cookie.getValue();
//            }
//        }

        //        // Header의 Authorization 값이 'Bearer '로 시작하지 않으면 => 잘못된 토큰
//        if(!authorizationHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // 전송받은 값에서 'Bearer ' 뒷부분(Jwt Token) 추출
//        String token = authorizationHeader.split(" ")[1];
//
//        // 전송받은 Jwt Token이 만료되었으면 => 다음 필터 진행(인증 X)
//        if(JwtTokenUtil.isExpired(token, secretKey)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
        // 2. 토큰이 없다면 다음 필터로 넘김
        //Authorization 헤더 검증
//        if (authorization == null) {
//
//            log.error("access token null in jwt token filter");
//            filterChain.doFilter(request, response);
//
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            //조건이 해당되면 메소드 종료 (필수)
//            return;
//        }

        //토큰
//        String token = authorization;

        // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
//        try{
//            jwtUtil.isExpired(token);
//        }catch(ExpiredJwtException e){
            //response body
//            PrintWriter writer = response.getWriter();
//            writer.print("access token expired");
//            log.error("access token expired in jwt token filter");
//
//            if (refresh == null) {
//                log.error("refresh token null in jwt token filter");
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                return;
//            }

            //expired check
//            try {
//                jwtUtil.isExpired(refresh);
//            } catch (ExpiredJwtException e2) {
//
//                log.error("refresh token expired in jwt token filter");
//                //response status code
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                return;
//            }

//            // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
//            String category = jwtUtil.getCategory(refresh);
//
//            if (!category.equals("refresh")) {
//
//                log.error("invalid refresh token in jwt token filter");
//                //response status code
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                return;
//            }
//
//            log.info("refresh token is alive!!");
//            token = reissueToken(refresh, response);
//            log.info("new Access Token: {}",token);
//        }
        //토큰 소멸 시간 검증
//        if (jwtUtil.isExpired(token)) {
//
//            System.out.println("token expired");
//            filterChain.doFilter(request, response);
//
//            //조건이 해당되면 메소드 종료 (필수)
//            return;
//        }

        // 토큰이 access인지 확인 (발급시 페이로드에 명시)
//        String category = jwtUtil.getCategory(accessToken);
//
//        if (!category.equals("access")) {
//
//            //response body
//            PrintWriter writer = response.getWriter();
//            writer.print("invalid access token");
//
//            //response status code
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }

//        String token = reissueToken(refresh);
//        log.info("befo access token: {}", token);
//        log.info("refresh token: {}", refresh);
        //토큰에서 username과 role 획득

        String username = jwtUtil.getUsername(token);
        Role role = jwtUtil.getRole(token);

        //userDTO를 생성하여 값 set
        UserDTO userDTO = UserDTO.builder()
                .username(username)
                .role(role)
                .build();

        //UserDetails에 회원 정보 객체 담기
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
        // 정상 토큰이면 SecurityContext에 저장
        //세션에 사용자 등록
//        response.addHeader("Authorization", "Bearer " + token);
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

//    private String reissueToken(String refreshToken, HttpServletResponse response){
//        String username = jwtUtil.getUsername(refreshToken);
//        Role role = jwtUtil.getRole(refreshToken);
//
//        //make new JWT
//        String newAccessToken = jwtUtil.createAccessToken(username, role);
//        String newRefreshToken = jwtUtil.createRefreshToken(username, role);
//
//        //response
//        response.setHeader("access", newAccessToken);
//        response.addCookie(jwtUtil.createCookie("Authorization", newAccessToken));
//        response.addCookie(jwtUtil.createCookie("refresh", newRefreshToken));
//
//        return newAccessToken;
//    }

//    private final MemberService userService;
//    private final String secretKey;

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//
//        // Header의 Authorization 값이 비어있으면 => Jwt Token을 전송하지 않음 => 로그인 하지 않음
//        if(authorizationHeader == null) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // Header의 Authorization 값이 'Bearer '로 시작하지 않으면 => 잘못된 토큰
//        if(!authorizationHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // 전송받은 값에서 'Bearer ' 뒷부분(Jwt Token) 추출
//        String token = authorizationHeader.split(" ")[1];
//
//        // 전송받은 Jwt Token이 만료되었으면 => 다음 필터 진행(인증 X)
//        if(JwtTokenUtil.isExpired(token, secretKey)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // Jwt Token에서 loginId 추출
//        String loginId = JwtTokenUtil.getLoginId(token, secretKey);
//
//        // 추출한 loginId로 User 찾아오기
//        Member loginUser = userService.getLoginUserByLoginId(loginId);
//
//        // loginUser 정보로 UsernamePasswordAuthenticationToken 발급
////        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
////                loginUser.getLoginId(), null, List.of(new SimpleGrantedAuthority(loginUser.getRole().name())));
////        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//        // 권한 부여
////        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//        filterChain.doFilter(request, response);
//    }
}
