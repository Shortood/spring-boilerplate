package com.example.project1.dto.response;

import com.example.project1.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserInfoDto {
    @JsonProperty("serialId")
    @NotNull
    private final String serialId;

    @JsonProperty("nickname")
    @NotNull
    private final String nickname;

    @JsonProperty("phoneNumber")
    @NotNull
    private final String phoneNumber;

    public static UserInfoDto EntityToDto(User user) {
        return new UserInfoDto(user.getSerialId(), user.getNickname(),
                user.getPhoneNumber());
    }
}
