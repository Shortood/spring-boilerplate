package com.example.project1.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DiaryDetailDto {
    @JsonProperty("title")
    @NotNull
    private final String title;

    @JsonProperty("content")
    @NotNull
    private final String content;

    @JsonProperty("date")
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final String date;
}
