package com.example.project1.service;


import com.example.project1.domain.User;
import com.example.project1.domain.type.ERole;
import com.example.project1.exception.CommonException;
import com.example.project1.exception.ErrorCode;
import com.example.project1.repository.DiaryRepository;
import com.example.project1.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserInfo(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
    }


}
