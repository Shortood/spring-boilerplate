package com.example.sontcamp.dto.response;

import com.example.sontcamp.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailDto {
    private Long id;

    public static UserDetailDto fromEntity(User user) {
        return UserDetailDto.builder()
                .id(user.getId())
                .build();
    }
}
