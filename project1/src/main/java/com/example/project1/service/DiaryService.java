package com.example.project1.service;

import com.example.project1.domain.Diary;
import com.example.project1.domain.User;
import com.example.project1.domain.type.ERole;
import com.example.project1.dto.PageInfo;
import com.example.project1.dto.request.DiaryRequestDto;
import com.example.project1.dto.request.DiaryUpdateDto;
import com.example.project1.dto.response.AllDto;
import com.example.project1.dto.response.DiaryDetailDto;
import com.example.project1.dto.response.DiaryListDto;
import com.example.project1.exception.CommonException;
import com.example.project1.exception.ErrorCode;
import com.example.project1.repository.DiaryRepository;
import com.example.project1.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    public Boolean createDiary(Long id, DiaryRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        diaryRepository.save(Diary.builder()
                .title(requestDto.title())
                .content(requestDto.content())
                .createdDate(requestDto.createdDate())
                .user(user).build());

        return Boolean.TRUE;
    }

    public DiaryDetailDto readDiaryDetail(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_DIARY));

        return new DiaryDetailDto(diary.getTitle(), diary.getContent(), diary.getCreatedDate().toString());
    }

    public AllDto<Object> readDiaryList(String word, Long pageIndex, Long pageSize) {
        final int PAGE_SIZE = 10;
        Pageable paging = PageRequest.of(pageIndex.intValue(), PAGE_SIZE);

        Page<Diary> diaries = diaryRepository.fullTextSearch(word, paging);

        PageInfo pageInfo = PageInfo.builder()
                .page(pageIndex.intValue())
                .size(PAGE_SIZE)
                .totalElements((int) diaries.getTotalElements())
                .totalPages(diaries.getTotalPages())
                .build();

        List<DiaryListDto> desireListDtos = diaries.stream()
                .map(d -> DiaryListDto.EntityToDto(d))
                .collect(Collectors.toList());

        return AllDto.builder()
                .dataList(desireListDtos)
                .pageInfo(pageInfo)
                .build();
    }

    public Boolean updateDiary(Long id, Long diaryId, DiaryUpdateDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        Diary diary = diaryRepository.findByIdAndUser(diaryId, user)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_DIARY));

        diary.update(requestDto.title(), requestDto.content());

        return Boolean.TRUE;
    }
    //test
    public Boolean deleteDiary(Long id, Long diaryId) {
        Diary diary = null;

        User user = userRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        if (user.getRole() != ERole.ADMIN)
            diary = diaryRepository.findByIdAndUser(diaryId, user)
                    .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_DIARY));
        else
            diary = diaryRepository.findById(diaryId)
                    .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_DIARY));


        diaryRepository.delete(diary);

        return Boolean.TRUE;
    }
}
