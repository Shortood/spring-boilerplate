package com.example.project1.controller;

import com.example.project1.annotation.UserId;
import com.example.project1.constant.Constants;
import com.example.project1.dto.request.UserSignUpDto;
import com.example.project1.dto.request.UserResisterDto;
import com.example.project1.dto.response.JwtTokenDto;
import com.example.project1.exception.CommonException;
import com.example.project1.exception.ErrorCode;
import com.example.project1.exception.ResponseDto;
import com.example.project1.service.AuthService;
import com.example.project1.util.HeaderUtil;
import com.example.project1.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    //회원가입
    @PostMapping("/basic")
    public ResponseDto<?> resisterUser(@RequestBody @Valid UserSignUpDto requestDto) {
        return ResponseDto.ok(authService.resisterUser(requestDto));
    }

    @PostMapping("/reissue")
    public ResponseDto<JwtTokenDto> reissue(final HttpServletRequest request) {
        final String refreshToken = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_HEADER));

        final JwtTokenDto jwtTokenDto = authService.reissue(refreshToken);
        return ResponseDto.created(jwtTokenDto);
    }

    //소셜 로그인 사용자 정보 등록
    @PatchMapping("/resister")
    public ResponseDto<?> resister(@UserId Long id, @RequestBody UserResisterDto requestDto) {
        return ResponseDto.created(authService.registerUserInfo(id, requestDto));
    }

    //회원탈퇴
    @DeleteMapping("/{userId}")
    public ResponseDto<?> withdrawUser(@UserId Long id, @PathVariable("userId") Long userId) {
        return ResponseDto.ok(authService.withdrawUser(id, userId));
    }

    //사용자 정보 수정
    @PatchMapping("/update")
    public ResponseDto<?> updateUserInfo(@UserId Long id, @RequestBody UserResisterDto requestDto) {
        return ResponseDto.created(authService.updateUserInfo(id, requestDto));
    }


}
