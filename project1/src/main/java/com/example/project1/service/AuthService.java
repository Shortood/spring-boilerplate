package com.example.project1.service;

import com.example.project1.domain.User;
import com.example.project1.domain.type.ERole;
import com.example.project1.dto.request.AuthSignUpDto;
import com.example.project1.dto.request.UserSignUpDto;
import com.example.project1.dto.request.UserResisterDto;
import com.example.project1.dto.response.JwtTokenDto;
import com.example.project1.exception.CommonException;
import com.example.project1.exception.ErrorCode;
import com.example.project1.repository.DiaryRepository;
import com.example.project1.repository.UserRepository;
import com.example.project1.util.JwtUtil;
import com.example.project1.util.OAuth2Util;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DiaryRepository diaryRepository;
    private final JwtUtil jwtUtil;

    public Boolean resisterUser(UserSignUpDto requestDto) {
        userRepository.findBySerialIdOrNickname(requestDto.serialId(), requestDto.nickname())
                .ifPresent(u -> {
                    throw new CommonException(ErrorCode.DUPLICATION_IDORNICKNAME);
                });

        userRepository.save(User.signUp(new AuthSignUpDto(requestDto.serialId(),
                        requestDto.nickname(), requestDto.phoneNumber()),
                passwordEncoder.encode(requestDto.password())));
        return Boolean.TRUE;
    }

    public Boolean withdrawUser(Long id, Long userId) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        if (!(id == userId || user.getRole() == ERole.ADMIN))
            throw new CommonException(ErrorCode.ACCESS_DENIED_ERROR);
        //일기는 삭제하지 않고 기본 유저 id로 전환
        diaryRepository.updateDiaryByUserId(userId);

        userRepository.delete(user);
        return Boolean.TRUE;
    }

    public JwtTokenDto registerUserInfo(Long userId, UserResisterDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        user.register(requestDto.nickname(), requestDto.phoneNumber());
        JwtTokenDto jwtTokenDto = jwtUtil.generateTokens(userId, ERole.USER);
        return jwtTokenDto;
    }

    public Boolean updateUserInfo(Long userId, UserResisterDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        user.updateInfo(requestDto.nickname(), requestDto.phoneNumber());

        return Boolean.TRUE;
    }

    @Transactional
    public JwtTokenDto reissue(final String refreshToken) {
        return jwtUtil.reissue(refreshToken);
    }
}
