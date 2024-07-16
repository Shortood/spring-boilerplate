package com.example.sontcamp.service;

import com.example.sontcamp.domain.User;
import com.example.sontcamp.dto.request.UserRequestDto;
import com.example.sontcamp.dto.response.UserDetailDto;
import com.example.sontcamp.dto.response.UserResponseDto;
import com.example.sontcamp.exception.CommonException;
import com.example.sontcamp.exception.ErrorCode;
import com.example.sontcamp.repository.UserMemoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMemoryRepository userMemoryRepository;

    public UserResponseDto createUser(UserRequestDto requestDto) {

        User user = User.builder()
                .serialId(requestDto.getSerialId())
                .password(requestDto.getPassword())
                .loginProvider(requestDto.getLoginProvider())
                .role(requestDto.getRole())
                .nickname(requestDto.getNickname())
                .build();

        userMemoryRepository.save(user);

        return UserResponseDto.fromEntity(user);
    }

    public UserDetailDto readUser(Long userId) {
        //과제 1번
        User user = userMemoryRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);

        return UserDetailDto.fromEntity(user);
    }

    public UserDetailDto readUser2(Long userId) {
        //과제 2번
        User user = userMemoryRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        return UserDetailDto.fromEntity(user);
    }

    public Boolean dateValidation(LocalDate date) {
        return Boolean.TRUE;
    }
}
