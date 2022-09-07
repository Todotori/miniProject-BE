package com.sparta.miniprojectbe.service;


import com.sparta.miniprojectbe.Util.S3Uploader;
import com.sparta.miniprojectbe.domain.dto.request.MemberUpdateRequestDto;
import com.sparta.miniprojectbe.domain.dto.response.MemberUpdateResponseDto;
import com.sparta.miniprojectbe.domain.entity.Member;
import com.sparta.miniprojectbe.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class MypageService {
    private final MemberRepository memberRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public MemberUpdateResponseDto updateMyPage(String userId, MemberUpdateRequestDto memberUpdateRequestDto, MultipartFile image){
        Member member = memberRepository.findByEmail(userId).orElseThrow();
        System.out.println("userid"+userId);
        System.out.println(memberUpdateRequestDto.getComment()+"sdf");
        System.out.println("member"+member);
        System.out.println(image);
        String storedFileName = member.getProfileImage();
        if (image != null) {
            try {
                storedFileName = s3Uploader.upload(image, "images");
                System.out.println(storedFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        member.update(memberUpdateRequestDto,storedFileName);
//        memberRepository.save(member);

        return new MemberUpdateResponseDto(member);
    }



}
