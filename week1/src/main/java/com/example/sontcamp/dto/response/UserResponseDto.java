package com.example.sontcamp.dto.response;

import com.example.sontcamp.domain.User;
import com.example.sontcamp.domain.type.EProvider;
import com.example.sontcamp.domain.type.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class UserResponseDto {
    private String serialId;
    private String password;
    private EProvider loginProvider;
    private ERole role;
    private String nickname;

    @Builder
    public UserResponseDto(String serialId, String password, EProvider loginProvider, ERole role, String nickname) {
        this.serialId = serialId;
        this.password = password;
        this.loginProvider = loginProvider;
        this.role = role;
        this.nickname = nickname;
    }

    public static UserResponseDto fromEntity(User user) {
        return UserResponseDto.builder()
                .serialId(user.getSerialId())
                .password(user.getPassword())
                .loginProvider(user.getLoginProvider())
                .role(user.getRole())
                .nickname(user.getNickname())
                .build();
    }
}
