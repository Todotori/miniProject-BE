package com.sparta.miniprojectbe.domain.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

  ENTITY_NOT_FOUND("NOT_FOUND", "데이터가 존재하지 않습니다."),

  NEED_LOGIN("NEED_LOGIN", "로그인이 필요합니다."),

  BAD_TOKEN_REQUEST("BAD_TOKEN_REQUEST", "토큰이 유효하지 않습니다."),

  TOKEN_NOT_FOUND("TOKEN_NOT_FOUND", "존재하지 않는 Token 입니다."),

  DUPLICATED_EMAIL("DUPLICATED_EMAIL", "중복된 이메일 입니다."),

  DUPLICATED_NICKNAME("DUPLICATED_NICKNAME","중복된 닉네임 입니다."),

  MEMBER_NOT_FOUND("MEMBER_NOT_FOUND", "사용자를 찾을 수 없습니다."),


  PASSWORDS_NOT_MATCHED("PASSWORDS_NOT_MATCHED", "비밀번호와 비밀번호 확인이 일치하지 않습니다."),


  NOT_SAME_MEMBER("NOT_SAME_MEMBER", "해당 작성자만 수정이 가능합니다."),

  INVALID_ERROR("INVALID_ERROR", "에러 발생");

//  NICKNAME_NOT_EXIST("NICKNAME_NOT_EXIST", "사용자를 찾을 수 없습니다.");



  private final String code;

  private final String message;

  ErrorCode(String code, String message){
    this.code = code;
    this.message = message;
  }

}
