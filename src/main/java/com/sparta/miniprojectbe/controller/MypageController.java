package com.sparta.miniprojectbe.controller;

import com.sparta.miniprojectbe.domain.dto.request.MemberUpdateRequestDto;
import com.sparta.miniprojectbe.domain.dto.response.MemberUpdateResponseDto;
import com.sparta.miniprojectbe.domain.dto.response.MypageResponseDto;
import com.sparta.miniprojectbe.domain.dto.response.ResponseDto;
import com.sparta.miniprojectbe.security.UserDetailsImpl;
import com.sparta.miniprojectbe.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/api/member")
    public ResponseDto<MypageResponseDto> mypage(@AuthenticationPrincipal UserDetailsImpl userDetails){
        MypageResponseDto mypageResponseDto;
        String userId = userDetails.getUsername();
        mypageResponseDto = mypageService.mypage(userId);
        return  new ResponseDto<>(mypageResponseDto);
    }

    @PutMapping("/api/member")
    public ResponseDto<MemberUpdateResponseDto> updateMypage(@RequestPart("data") MemberUpdateRequestDto requestDto,
                                                             @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                             @RequestPart(required = false) MultipartFile image) {
        MemberUpdateResponseDto memberUpdateResponseDto;
        String userId = userDetails.getUsername();
        memberUpdateResponseDto = mypageService.updateMyPage(userId,requestDto,image);
        return new ResponseDto<>(memberUpdateResponseDto);
    }


    }


