package com.sparta.miniprojectbe.service;

import com.sparta.miniprojectbe.domain.entity.Member;
import com.sparta.miniprojectbe.domain.entity.TokenDto;
import com.sparta.miniprojectbe.domain.entity.request.CheckRequestDto;
import com.sparta.miniprojectbe.domain.entity.request.LoginRequestDto;
import com.sparta.miniprojectbe.domain.entity.request.MemberRequestDto;
import com.sparta.miniprojectbe.domain.entity.response.MemberResponseDto;
import com.sparta.miniprojectbe.domain.entity.response.ResponseDto;
import com.sparta.miniprojectbe.jwt.TokenProvider;
import com.sparta.miniprojectbe.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    // 회원가입
    public ResponseDto<?> createMember(MemberRequestDto requestDto) {

        if (null != isPresenEmail(requestDto.getEmail())) {
            return ResponseDto.fail("DUPLICATED_EMAIL",
                    "중복된 이메일 입니다.");
        }

        if (null != isPresenNickname(requestDto.getNickname())) {
            return ResponseDto.fail("DUPLICATED_NICKNAME",
                    "중복된 닉네임 입니다.");
        }

        if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
            return ResponseDto.fail("PASSWORDS_NOT_MATCHED",
                    "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        Member member = Member.builder()
                .email(requestDto.getEmail())
                .nickname(requestDto.getNickname())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .build();
        memberRepository.save(member);
        return ResponseDto.success(
                MemberResponseDto.builder()
                        .id(member.getId())
                        .email(requestDto.getEmail())
                        .nickname(member.getNickname())
                        .build()
        );
    }

    // 이메일 중복체크
    public boolean emailck(CheckRequestDto requestDto){
        String email = requestDto.getEmail();
        if (memberRepository.existsByEmail(email)) {
            return false;
        }
        return true;
    }

    // 닉네임 중복체크
    public boolean nickck(CheckRequestDto requestDto){
        String nickname = requestDto.getNickname();
        if (memberRepository.existsByNickname(nickname)) {
            return false;
        }
        return true;
    }

    // DB에 email이 있는지 확인
    public Member isPresenEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.orElse(null);
    }

    // DB에 nickname이 있는지 확인
    public Member isPresenNickname(String nickname) {
        Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
        return optionalMember.orElse(null);
    }

    // 로그인
    public ResponseDto<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
        Member member = isPresenEmail(requestDto.getEmail());
        if (null == member) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "사용자를 찾을 수 없습니다.");
        }

        if (!member.validatePassword(passwordEncoder, requestDto.getPassword())) {
            return ResponseDto.fail("INVALID_MEMBER", "사용자를 찾을 수 없습니다2.");
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        tokenToHeaders(tokenDto, response);

        return ResponseDto.success(
                MemberResponseDto.builder()
                        .id(member.getId())
                        .email(member.getEmail())
                        .nickname(member.getNickname())
                        .build()
        );
    }

    // 헤더에 토큰삽입
    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }

}
