package com.sparta.miniprojectbe.controller;

import com.sparta.miniprojectbe.domain.dto.request.CheckRequestDto;
import com.sparta.miniprojectbe.domain.dto.request.LoginRequestDto;
import com.sparta.miniprojectbe.domain.dto.request.MemberRequestDto;
import com.sparta.miniprojectbe.domain.dto.response.ResponseDto;
import com.sparta.miniprojectbe.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/api/signup")
    public ResponseDto<?> signup(@RequestBody MemberRequestDto requestDto) {
        return memberService.createMember(requestDto);
    }

    // 로그인
    @PostMapping("/api/login")
    public ResponseDto<?> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        return memberService.login(requestDto, response);
    }

    // 이메일 중복확인
    @PostMapping("/api/emailcheck")
    public boolean emailcheck(@RequestBody CheckRequestDto requestDto) {
        return memberService.emailCheck(requestDto);
    }

    // 닉네임 중복확인
    @PostMapping("/api/nickcheck")
    public boolean nickcheck(@RequestBody CheckRequestDto requestDto) {
        return memberService.nickCheck(requestDto);
    }

}
