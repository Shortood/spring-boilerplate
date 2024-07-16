package com.example.project1.dto.response;

import com.example.project1.domain.Diary;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@RequiredArgsConstructor
public class DiaryListDto {
    @JsonProperty("id")
    @NotNull
    private final Long id;

    @JsonProperty("title")
    @NotNull
    private final String title;

    @JsonProperty("content")
    @NotNull
    private final String content;

    @JsonProperty("date")
    @NotNull
    private final String date;

    public static DiaryListDto EntityToDto(Diary diary) {
        return new DiaryListDto(diary.getId(), diary.getTitle(),
                diary.getContent(), diary.getCreatedDate().toString());
    }

}
