package com.sparta.miniprojectbe.domain.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

  ENTITY_NOT_FOUND("NOT_FOUND", "데이터가 존재하지 않습니다."),

  NOT_SAME_MEMBER("NOT_SAME_MEMBER", "해당 작성자만 수정이 가능합니다."),

  INVALID_ERROR("INVALID_ERROR", "에러 발생"),

  NICKNAME_NOT_EXIST("NICKNAME_NOT_EXIST", "사용자를 찾을 수 없습니다.");





  private final String code;

  private final String message;

  ErrorCode(String code, String message){
    this.code = code;
    this.message = message;
  }

}
