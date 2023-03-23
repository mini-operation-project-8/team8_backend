package com.example.cooperation_project.dto.post;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReqPostDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;
}
