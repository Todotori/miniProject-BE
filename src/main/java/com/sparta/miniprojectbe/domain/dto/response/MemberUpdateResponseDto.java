package com.sparta.miniprojectbe.domain.dto.response;

import com.sparta.miniprojectbe.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateResponseDto {
    private Long id;
    private String company;
    private String comment;
    private String profileImage;

    public MemberUpdateResponseDto(Member member) {
        this.id = member.getId();
        this.company = member.getCompany();
        this.comment = member.getComment();
        this.profileImage = member.getProfileImage();
    }
//
//    public MemberUpdateResponseDto(Long id,String company,String comment, String ProfileImage) {
//        this.id = id;
//        this.company = company;
//        this.comment = comment;
//        this.profileImage = ProfileImage;
//    }
}