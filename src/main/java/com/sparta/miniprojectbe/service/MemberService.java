package com.sparta.miniprojectbe.service;

import com.sparta.miniprojectbe.domain.dto.request.CheckRequestDto;
import com.sparta.miniprojectbe.domain.dto.request.LoginRequestDto;
import com.sparta.miniprojectbe.domain.dto.request.MemberRequestDto;
import com.sparta.miniprojectbe.domain.dto.response.MemberResponseDto;
import com.sparta.miniprojectbe.domain.dto.response.ResponseDto;
import com.sparta.miniprojectbe.domain.entity.Member;
import com.sparta.miniprojectbe.domain.entity.TokenDto;
import com.sparta.miniprojectbe.domain.enums.ErrorCode;
import com.sparta.miniprojectbe.jwt.TokenProvider;
import com.sparta.miniprojectbe.repository.MemberRepository;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    // 회원가입
    public ResponseDto<?> createMember(MemberRequestDto requestDto, HttpServletResponse response) {

        if (null != isPresentEmail(requestDto.getEmail())) {
            return new ResponseDto<>(null, ErrorCode.DUPLICATED_EMAIL);
        }

        if (null != isPresentNickname(requestDto.getNickname())) {
            return new ResponseDto<>(null,ErrorCode.DUPLICATED_NICKNAME);
        }

        if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
            return new ResponseDto<>(null,ErrorCode.PASSWORDS_NOT_MATCHED);
        }
        String basicProfileImage = "https://test-bucket-jaewon.s3.ap-northeast-2.amazonaws.com/images/1c336d55-b902-4862-83b1-02c8465d55bc%EA%B8%B0%EB%B3%B8%EC%9D%B4%EB%AF%B8%EC%A7%80.jpg";
        Member member = new Member(requestDto.getEmail(), requestDto.getNickname(),
            passwordEncoder.encode(requestDto.getPassword()),basicProfileImage);

        memberRepository.save(member);
        Member tokenMember = isPresentEmail(requestDto.getEmail());
        TokenDto tokenDto = tokenProvider.generateTokenDto(tokenMember);
        tokenToHeaders(tokenDto, response);
        MemberResponseDto memberResponseDto = new MemberResponseDto(member.getId(), requestDto.getEmail(), member.getNickname(),member.getCreatedAt(),member.getModifiedAt());

        return new ResponseDto<>(memberResponseDto);
    }

    // 이메일 중복체크
    public boolean emailCheck(CheckRequestDto requestDto){
        String email = requestDto.getEmail();
        return !memberRepository.existsByEmail(email);
    }

    // 닉네임 중복체크
    public boolean nickCheck(CheckRequestDto requestDto){
        String nickname = requestDto.getNickname();
        return !memberRepository.existsByNickname(nickname);
    }

    // DB에 email이 있는지 확인
    public Member isPresentEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.orElse(null);
    }

    // DB에 nickname이 있는지 확인
    public Member isPresentNickname(String nickname) {
        Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
        return optionalMember.orElse(null);
    }

    // 로그인
    public ResponseDto<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
        Member member = isPresentEmail(requestDto.getEmail());
        if (null == member) {
            return new ResponseDto<>(null,ErrorCode.MEMBER_NOT_FOUND );
        }

        if (!member.validatePassword(passwordEncoder, requestDto.getPassword())) {
            return  new ResponseDto<>(null,ErrorCode.NOT_SAME_PASSWORD );
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        tokenToHeaders(tokenDto, response);

        MemberResponseDto memberResponseDto = new MemberResponseDto(member.getId(), requestDto.getEmail(), member.getNickname(),member.getCreatedAt(),member.getModifiedAt());

        return new ResponseDto<>(memberResponseDto);
    }

    // 헤더에 토큰삽입
    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }

}
