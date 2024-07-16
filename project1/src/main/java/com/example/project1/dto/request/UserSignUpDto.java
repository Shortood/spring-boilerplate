package com.example.project1.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record UserSignUpDto(@JsonProperty("serialId")
                            @NotNull
                            @NotBlank
                            String serialId,
                            @JsonProperty("password")
                            @NotNull
                            @NotBlank
                            String password,
                            @JsonProperty("nickname")
                            @NotNull
                            @NotBlank
                            String nickname,
                            @JsonProperty("phoneNumber")
                            @NotNull
                            @NotBlank
                            String phoneNumber) {

}
