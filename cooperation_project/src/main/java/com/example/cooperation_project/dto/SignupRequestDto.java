package com.example.cooperation_project.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    @NotNull(message = "userId를 입력해주세요.")
    @Size(min = 4, max = 10)
    @Pattern(regexp = "[a-z0-9]+")
    private  String userId;

    @NotNull(message = "password를 입력해주세요.")
    @Size(min = 8, max = 15)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+])[a-zA-Z\\d!@#$%^&*()_+]{8,15}$\n")  // validation. entity의 목적- DB와 연결하는 데이터의 모음..DTO의 목적 - Data를 움직이는 목적 외부에서 오는 데이터를 여기서 체크.
    private String password;

    private boolean admin = false;
    private String adminToken ="";
}
