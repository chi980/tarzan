package com.mjutarzan.tarzan.global.security.jwt;

import com.mjutarzan.tarzan.domain.member.entity.CustomOAuth2User;
import com.mjutarzan.tarzan.domain.member.model.dto.UserDTO;
import com.mjutarzan.tarzan.domain.member.model.vo.Role;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 위에서 언급한 바와 같이 사용자의 요청에서 Jwt Token을 추출해 통과하면 권한을 부여하고 실패하면 권한을 부여하지 않고 다음 필터로 진행시킴
 */
@RequiredArgsConstructor
//@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtUtil;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Header에서 accessToken을 꺼냄
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("doFilterInternal");

        System.out.println(authorizationHeader);
        //cookie들을 불러온 뒤 Authorization Key에 담긴 쿠키를 찾음
        String authorization = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {

            System.out.println(cookie.getName());
            if (cookie.getName().equals("Authorization")) {

                authorization = cookie.getValue();
            }
        }

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
        if (authorization == null) {

            System.out.println("token null");
            filterChain.doFilter(request, response);
            // 토큰을 받아오는 로직 작성
            //

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        //토큰
        String token = authorization;

        // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        try{
            jwtUtil.isExpired(token);
        }catch(ExpiredJwtException e){
            //response body
            PrintWriter writer = response.getWriter();
            writer.print("access token expired");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
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

        //토큰에서 username과 role 획득
        String username = jwtUtil.getUsername(token);
        String roleString = jwtUtil.getRole(token);

        //userDTO를 생성하여 값 set
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setRole(Role.valueOf(roleString));

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
