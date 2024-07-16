package com.example.project1.controller;

import com.example.project1.annotation.UserId;
import com.example.project1.domain.User;
import com.example.project1.dto.response.UserInfoDto;
import com.example.project1.exception.ResponseDto;
import com.example.project1.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //유저정보 불러오기
    @GetMapping("")
    public ResponseDto<UserInfoDto> getUserInfo(@UserId Long id) {
        final User user = userService.getUserInfo(id);
        final UserInfoDto userInfoDto = UserInfoDto.EntityToDto(user);

        return ResponseDto.ok(userInfoDto);
    }



}
