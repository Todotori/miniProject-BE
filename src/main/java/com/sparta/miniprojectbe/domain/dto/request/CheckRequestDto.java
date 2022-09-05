package com.sparta.miniprojectbe.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckRequestDto {

    private String email;
    private String nickname;

}
