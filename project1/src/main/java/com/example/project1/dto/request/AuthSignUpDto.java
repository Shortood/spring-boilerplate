package com.example.project1.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record AuthSignUpDto(@JsonProperty("serialId")
                            @NotNull
                            @NotBlank
                            String serialId,
                            @JsonProperty("nickname")
                            @NotNull
                            @NotBlank
                            String nickname,
                            @JsonProperty("phoneNumber")
                            @NotNull
                            @NotBlank
                            String phoneNumber) {
}
