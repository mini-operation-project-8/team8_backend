package com.example.cooperation_project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MsgCodeResponseDto {

    private String msg;
    private int statusCode;

    public void setResult(String msg, int statusCode){
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
