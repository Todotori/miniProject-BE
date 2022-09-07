package com.sparta.miniprojectbe.domain.dto.response;

import com.sparta.miniprojectbe.domain.entity.Member;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MemberResponseDto {

  private Long id;
  private String nickname;
  private String email;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

//  private LocalDateTime createdAt;
//  private LocalDateTime modifiedAt;

//  public MemberResponseDto(Member member) {
//    this.id = member.getId();
//    this.nickname = member.getNickname();
//    this.email = member.getEmail();
//    this.createdAt = member.getCreatedAt();
//    this.modifiedAt = member.getModifiedAt();
//  }

  public MemberResponseDto(Member member){
    this.id = member.getId();
    this.email = member.getEmail();
    this.nickname = member.getNickname();
    this.createdAt = member.getCreatedAt();
    this.modifiedAt = member.getModifiedAt();
  }

  public MemberResponseDto(Long id,String email,String nickname,LocalDateTime createdAt,LocalDateTime modifiedAt ){
    this.id = id;
    this.email = email;
    this.nickname = nickname;
    this.createdAt = createdAt;
    this.modifiedAt = modifiedAt;
  }

}
