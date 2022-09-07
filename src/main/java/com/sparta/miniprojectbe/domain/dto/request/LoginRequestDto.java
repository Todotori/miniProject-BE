package com.sparta.miniprojectbe.domain.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    private String email;
    private String password;

}
