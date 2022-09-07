package com.sparta.miniprojectbe.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @Pattern(regexp = "^[A-Za-z0-9ㄱ-ㅎ|ㅏ-ㅣ|가-힣]{5,10}$", message = "닉네임은 5~10글자이며 특수문자는 사용이 불가능 합니다.")
    private String nickname;

    @Pattern(regexp = "^[A-Za-z0-9!@#$%^&*]{5,15}$", message = "비밀번호는 5~15글자이며 한글은 사용이 불가능 합니다.")
    private String password;

    @Pattern(regexp = "^[A-Za-z0-9!@#$%^&*]{5,15}$", message = "비밀번호는 5~15글자이며 한글은 사용이 불가능 합니다.")
    private String passwordConfirm;

}
