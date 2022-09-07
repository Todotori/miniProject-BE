package com.sparta.miniprojectbe.domain.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sparta.miniprojectbe.domain.dto.request.MemberUpdateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.Store;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member extends Timestamped{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String nickname;

  @Column(nullable = false)
  private String password;

  @Column(unique = true, nullable = false)
  private String email;

  @Column
  private String company;

  @Column
  private String comment;

  @Column
  private String profileImage;

  public Member (String email, String nickname, String password, String basicProfileImage){
    this.email = email;
    this.nickname = nickname;
    this.password = password;
    this.profileImage = basicProfileImage;
  }

  public boolean validatePassword(PasswordEncoder passwordEncoder, String password) {
    return passwordEncoder.matches(password, this.password);
  }

  public void update(MemberUpdateRequestDto memberUpdateRequestDto, String storedFileName){
    this.company = memberUpdateRequestDto.getCompany();
    this.comment = memberUpdateRequestDto.getComment();
    this.profileImage = storedFileName;
  }

}
