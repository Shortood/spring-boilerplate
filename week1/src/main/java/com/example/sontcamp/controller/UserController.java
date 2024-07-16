package com.example.sontcamp.controller;

import com.example.sontcamp.annotation.Date;
import com.example.sontcamp.annotation.UserId;
import com.example.sontcamp.dto.request.UserRequestDto;
import com.example.sontcamp.exception.CommonException;
import com.example.sontcamp.exception.ErrorCode;
import com.example.sontcamp.exception.ResponseDto;
import com.example.sontcamp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public ResponseDto<?> createUser(@RequestBody @Valid UserRequestDto requestDto) {
        return ResponseDto.ok(userService.createUser(requestDto));
    }

    //과제 1번
    @GetMapping("")
    public ResponseDto<?> readUser() {
        Long userId = 1L;
        return ResponseDto.ok(userService.readUser(userId));
    }

    //과제 2번
    @GetMapping("/2")
    public ResponseDto<?> readUser2(@UserId Long userId) {
        return ResponseDto.ok(userService.readUser2(userId));
    }

    // 과제 2번용
    @GetMapping("/exception")
    public ResponseDto<?> readException() {
        throw new CommonException(ErrorCode.NOT_FOUND_USER);
    }

    @GetMapping("/date")
    public ResponseDto<?> checkDate(@RequestParam("date") @Date LocalDate date) {
        return ResponseDto.ok(userService.dateValidation(date));
    }
}
