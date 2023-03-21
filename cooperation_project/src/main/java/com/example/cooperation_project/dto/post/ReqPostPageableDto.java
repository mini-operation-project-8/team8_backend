package com.example.cooperation_project.dto.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
public class ReqPostPageableDto {

    private final String sortBy;

    private final boolean isAsc;

    private final int size;

    private final int page;

}
