package com.mjutarzan.tarzan.domain.member.service;

import com.mjutarzan.tarzan.domain.member.entity.CustomOAuth2User;
import com.mjutarzan.tarzan.domain.member.entity.Member;
import com.mjutarzan.tarzan.domain.member.model.dto.GoogleResponse;
import com.mjutarzan.tarzan.domain.member.model.dto.KakaoResponse;
import com.mjutarzan.tarzan.domain.member.model.dto.OAuth2Response;
import com.mjutarzan.tarzan.domain.member.model.dto.UserDTO;
import com.mjutarzan.tarzan.domain.member.model.vo.Role;
import com.mjutarzan.tarzan.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println(oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }else if(registrationId.equals("kakao")){
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        }
        else {

            return null;
        }

        //리소스 서버에서 발급 받은 정보로 사용자를 특정할 아이디값을 만듬
        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        Optional<Member> existData = memberRepository.findByUsername(username);
        if (existData.isEmpty()) {

            Role role = null;
            if(registrationId.equals("google")){
                role = Role.GOOGLE;
            }else if(registrationId.equals("kakao")){
                role = Role.KAKAO;
            }

            Member member = Member.builder()
                    .username(username)
                    .email(oAuth2Response.getEmail())
                    .name(oAuth2Response.getName())
                    .role(role)
                    .build();

            memberRepository.save(member);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(username);
            userDTO.setName(oAuth2Response.getName());
            userDTO.setRole(Role.GOOGLE);

            return new CustomOAuth2User(userDTO);
        }
        else {
            Member alreadyMember = existData.get();
            alreadyMember.update(oAuth2Response.getEmail(), oAuth2Response.getName());

            memberRepository.save(alreadyMember);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(alreadyMember.getUsername());
            userDTO.setName(oAuth2Response.getName());
            userDTO.setRole(alreadyMember.getRole());

            return new CustomOAuth2User(userDTO);
        }
    }
}