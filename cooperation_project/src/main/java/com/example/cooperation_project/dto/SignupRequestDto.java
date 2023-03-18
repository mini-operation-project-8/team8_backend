package com.example.cooperation_project.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    @NotBlank(message = "userId를 입력해주세요.")
    @Size(min = 4, max = 10)
    @Pattern(regexp = "[a-z0-9]+")
    private  String userId;

    @NotBlank(message = "password를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+])[a-zA-Z\\d!@#$%^&*()_+]{8,15}$")
    private String password;
//    (?=.*[a-z]): 소문자가 적어도 1개 이상 포함되어야 합니다.
//            (?=.*[A-Z]): 대문자가 적어도 1개 이상 포함되어야 합니다.
//            (?=.*\d): 숫자가 적어도 1개 이상 포함되어야 합니다.
//            (?=.*[^\\da-zA-Z]): 특수 문자가 적어도 1개 이상 포함되어야 합니다. \\d는 숫자를 의미하고, a-zA-Z는 알파벳 문자를 의미합니다. [^\\da-zA-Z]는 숫자와 알파벳 문자를 제외한 모든 문자를 의미합니다.
//            .{8,15}: 8자 이상 15자 이하의 문자열이어야 합니다. .은 어떤 문자든지 가능하다는 것을 의미합니다.

    private boolean admin = false;
    private String adminToken ="";


}
