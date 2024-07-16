package com.example.project1.domain.type;

import com.example.project1.exception.CommonException;
import com.example.project1.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
@Getter
@AllArgsConstructor
public enum ERole {
    GUEST("ROLE_GUEST", "GUEST"), USER("ROLE_USER", "USER"), ADMIN("ROLE_ADMIN", "ADMIN");

    private final String roleCode;
    private final String displayName;

    public static ERole of(String roleCode) {
        return Arrays.stream(ERole.values())
                .filter(v -> v.getRoleCode().equals(roleCode))
                .findAny()
                .orElseThrow(() -> new CommonException(ErrorCode.ACCESS_DENIED_ERROR));
    }
}
