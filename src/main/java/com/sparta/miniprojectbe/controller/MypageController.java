package com.sparta.miniprojectbe.controller;

import com.sparta.miniprojectbe.domain.dto.request.MemberUpdateRequestDto;
import com.sparta.miniprojectbe.domain.dto.response.MemberUpdateResponseDto;
import com.sparta.miniprojectbe.domain.dto.response.ResponseDto;
import com.sparta.miniprojectbe.security.UserDetailsImpl;
import com.sparta.miniprojectbe.service.MypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MypageController {

  private final MypageService mypageService;

  @PutMapping("/api/member")
  public ResponseDto<MemberUpdateResponseDto> updateMypage(
      @RequestPart("data") MemberUpdateRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestPart(required = false) MultipartFile image) {
    MemberUpdateResponseDto memberUpdateResponseDto;
    String userId = userDetails.getUsername();
    memberUpdateResponseDto = mypageService.updateMyPage(userId, requestDto, image);
    return new ResponseDto<>(memberUpdateResponseDto);
  }

}


