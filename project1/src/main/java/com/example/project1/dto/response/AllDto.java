package com.example.project1.dto.response;

import com.example.project1.dto.PageInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class AllDto<T> {
    private T dataList;
    private PageInfo pageInfo;

    @Builder
    public AllDto(T dataList, PageInfo pageInfo) {
        this.dataList = dataList;
        this.pageInfo = pageInfo;
    }
}
