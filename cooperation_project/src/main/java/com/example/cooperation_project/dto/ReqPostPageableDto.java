package com.example.cooperation_project.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class ReqPostPageableDto {

    private final String sortBy;

    private final boolean isAsc;

    private final int size;

    private final int page;

}
