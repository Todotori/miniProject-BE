package com.sparta.miniprojectbe.domain.dto.response;

import com.sparta.miniprojectbe.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
public class MypageResponseDto {

    private String nickname;
    private String email;
    private String company;
    private String comment;
    private String profileImage;

    public MypageResponseDto(Member member) {
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.company = member.getCompany();
        this.comment = member.getComment();
        this.profileImage = member.getProfileImage();

    }
}
