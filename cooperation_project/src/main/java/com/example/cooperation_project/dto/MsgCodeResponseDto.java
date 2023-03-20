package com.example.cooperation_project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class MsgCodeResponseDto {
    private final String msg;

    public MsgCodeResponseDto(String msg) {
        this.msg = msg;
    }




}
