package com.mjutarzan.tarzan.domain.member.service;

import com.mjutarzan.tarzan.domain.member.api.dto.request.SignupRequestDto;
import com.mjutarzan.tarzan.domain.member.api.dto.response.SignupResponseDto;
import com.mjutarzan.tarzan.domain.member.entity.Member;
import com.mjutarzan.tarzan.domain.member.model.vo.Role;
import com.mjutarzan.tarzan.domain.member.repository.MemberRepository;
import com.mjutarzan.tarzan.global.exception.DuplicateUserException;
import com.mjutarzan.tarzan.global.exception.ProviderNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberServiceImpl implements MemberService, UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public Member getLoginUserByLoginId(String loginId) {

        Optional<Member> loginUser = memberRepository.findByProviderId(loginId);

        if(!loginUser.isEmpty()) return loginUser.get();
        return null;
    }

    @Override
    public boolean checkProviderIdDuplicate(String providerId){
        Optional<Member> loginUser = memberRepository.findByProviderId(providerId);

        try{
            loginUser.get();
            return true;
        }catch(NoSuchElementException e){
            return false;
        }

    }

    @Override
    public boolean checkNicknameDuplicate(String nickname){
        log.info("받은 nickname:{}", nickname);
        log.info("첫번쨰 사용자:{}","sadfasfdasdfasdfdf");
        log.info("동일여부: {}", "sadfasfdasdfasdfdf".equals(nickname));
        Optional<Member> loginUser = memberRepository.findByNickname(nickname);
        Optional<Member> firstUser = memberRepository.findByNickname("sadfasfdasdfasdfdf");
        try{
            log.info("첫 꺼내진 사용자: {}",firstUser.get());
            loginUser.get();
            log.info("꺼내진 사용자: {}", loginUser.get().toString());
            return true;
        }catch(NoSuchElementException e){
            return false;
        }
    }

    public void signupV1(SignupRequestDto signupUserRequestDto) throws ProviderNotFoundException{
        Role role = null;
        if(signupUserRequestDto.getProvider().equals("google")) role = Role.GOOGLE;
        else if(signupUserRequestDto.getProvider().equals("kakao")) role = Role.KAKAO;
        else {
            throw new ProviderNotFoundException("지원하지 않는 provider입니다.");
        }

        Member user = Member.builder()
                .email(signupUserRequestDto.getEmail())
                .provider(signupUserRequestDto.getProvider())
                .providerId(signupUserRequestDto.getProviderId())
                .nickname(signupUserRequestDto.getNickname())
                .role(role)
                .gu(signupUserRequestDto.getGu())
                .haveAnimal(signupUserRequestDto.isHaveAnimal())
                .haveCar(signupUserRequestDto.isHaveCar())
                .build();
        memberRepository.save(user);
    }


    @Override
    public SignupResponseDto signup(SignupRequestDto signupUserRequestDto) throws DuplicateUserException, ProviderNotFoundException{
        if(checkProviderIdDuplicate(signupUserRequestDto.getProviderId())){
            throw new DuplicateUserException("중복된 사용자입니다.");
        }

        if(checkNicknameDuplicate(signupUserRequestDto.getNickname())){
            throw new DuplicateUserException("닉네임이 중복됩니다.");
        }

        Role role = null;
        if(signupUserRequestDto.getProvider().equals("google")) role = Role.GOOGLE;
        else if(signupUserRequestDto.getProvider().equals("kakao")) role = Role.KAKAO;
        else {
            throw new ProviderNotFoundException("지원하지 않는 provider입니다.");
        }

        Member member = Member.builder()
                .email(signupUserRequestDto.getEmail())
                .provider(signupUserRequestDto.getProvider())
                .providerId(signupUserRequestDto.getProviderId())
//                oauth 로그인만 이용하므로 비밀번호는 provider_email형식으로 저장
                .password(signupUserRequestDto.getProvider()+"_"+signupUserRequestDto.getEmail())
                .nickname(signupUserRequestDto.getNickname())
                .role(role)
                .gu(signupUserRequestDto.getGu())
                .haveAnimal(signupUserRequestDto.isHaveAnimal())
                .haveCar(signupUserRequestDto.isHaveCar())
                .build();
        memberRepository.save(member);


        return SignupResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .providerId(member.getProviderId())
                .gu(member.getGu())
                .haveAnimal(member.isHaveAnimal())
                .haveCar(member.isHaveCar())
                .build();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByProviderId(username).orElseThrow(()->new UsernameNotFoundException(username));
    }
}
