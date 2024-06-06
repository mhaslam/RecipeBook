package com.malcolmhaslam.recipebook.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String token;
    private long expirationTime;
    private UserDto user;

    public LoginResponseDto(String token, long expirationTime, UserDto user) {
        this.token = token;
        this.expirationTime = expirationTime;
        this.user = user;
    }

}
