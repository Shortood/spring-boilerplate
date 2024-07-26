package com.example.project1.controller;

import com.example.project1.annotation.UserId;
import com.example.project1.dto.request.DiaryRequestDto;
import com.example.project1.dto.request.DiaryUpdateDto;
import com.example.project1.exception.ResponseDto;
import com.example.project1.service.DiaryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    //일기 작성
    @PostMapping("")
    public ResponseDto<?> createDiary(@UserId Long id, @RequestBody @Valid DiaryRequestDto requestDto) {
        return ResponseDto.ok(diaryService.createDiary(id, requestDto));
    }
//test
    //일기 상세 일기
    @GetMapping("/{diaryId}")
    public ResponseDto<?> readDiaryDetail(@PathVariable("diaryId") Long diaryId) {
        return ResponseDto.ok(diaryService.readDiaryDetail(diaryId));
    }

    //일기 목록 불러오기
    @GetMapping("")
    public ResponseDto<?> readDiaryList(@RequestParam(value = "word",defaultValue = "") String word, @RequestParam(value = "index") @Min(0) Long pageIndex, @RequestParam(value = "size",defaultValue = "10") @Min(1) Long pageSize) {
        return ResponseDto.ok(diaryService.readDiaryList(word, pageIndex, pageSize));
    }

    //일기 수정
    @PatchMapping("/{diaryId}")
    public ResponseDto<?> updateDiary(@UserId Long id, @PathVariable("diaryId") Long diaryId, @RequestBody @Valid DiaryUpdateDto requestDto) {
        return ResponseDto.ok(diaryService.updateDiary(id, diaryId, requestDto));
    }

    //일기 삭제
    @DeleteMapping("/{diaryId}")
    public ResponseDto<?> deleteDiary(@UserId Long id, @PathVariable("diaryId") Long diaryId) {
        return ResponseDto.ok(diaryService.deleteDiary(id, diaryId));
    }


}
