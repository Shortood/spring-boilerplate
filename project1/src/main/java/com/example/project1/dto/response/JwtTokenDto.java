package com.example.project1.dto.response;

import com.example.project1.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtTokenDto extends SelfValidating<JwtTokenDto> {
    @NotNull String accessToken;
    @NotNull String refreshToken;

    @Builder
    public JwtTokenDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
