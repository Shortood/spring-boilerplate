package com.example.sontcamp.dto.request;

import com.example.sontcamp.domain.type.EProvider;
import com.example.sontcamp.domain.type.ERole;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    @NotNull
    @Size(min = 1)
    private String serialId;
    @NotNull
    @Size(min = 1)
    private String password;

    @NotNull
    private EProvider loginProvider;

    @NotNull
    private ERole role;

    private String nickname;
}
