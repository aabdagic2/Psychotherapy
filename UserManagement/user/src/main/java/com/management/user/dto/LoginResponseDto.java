package com.management.user.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginResponseDto {
    private String accessToken;

    public LoginResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
