package com.julyun.to_do_list_dto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDTO {
    @NotBlank(message = "아이디를 입력하세요")
    @Size(min=3, max=8, message = "3 ~ 8자 제한입니다.")
    private String username;

    @NotBlank(message = "비밀번호를 입력하세요")
    @Size(min=6, max=20, message = "6 ~ 20자 제한입니다.")
    private String password;
}
