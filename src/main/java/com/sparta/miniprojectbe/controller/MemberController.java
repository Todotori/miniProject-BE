package com.sparta.miniprojectbe.controller;

import com.sparta.miniprojectbe.domain.dto.request.CheckRequestDto;
import com.sparta.miniprojectbe.domain.dto.request.LoginRequestDto;
import com.sparta.miniprojectbe.domain.dto.request.MemberRequestDto;
import com.sparta.miniprojectbe.domain.dto.response.ResponseDto;
import com.sparta.miniprojectbe.domain.entity.Member;
import com.sparta.miniprojectbe.security.UserDetailsImpl;
import com.sparta.miniprojectbe.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/api/signup")
    public ResponseDto<?> signup(@Validated @RequestBody MemberRequestDto requestDto, HttpServletResponse response) {
        return memberService.createMember(requestDto, response);
    }

    // 로그인
    @PostMapping("/api/login")
    public ResponseDto<?> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        return memberService.login(requestDto, response);
    }

    // 이메일 중복확인
    @PostMapping("/api/emailcheck")
    public boolean emailCheck(@Validated @RequestBody CheckRequestDto requestDto) {
        return memberService.emailCheck(requestDto);
    }

    // 닉네임 중복확인
    @PostMapping("/api/nickcheck")
    public boolean nickCheck(@Validated @RequestBody CheckRequestDto requestDto) {
        return memberService.nickCheck(requestDto);
    }


}
