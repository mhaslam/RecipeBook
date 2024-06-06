package com.malcolmhaslam.recipebook.controller;

import com.malcolmhaslam.recipebook.dto.LoginRequestDto;
import com.malcolmhaslam.recipebook.dto.LoginResponseDto;
import com.malcolmhaslam.recipebook.dto.UserDto;
import com.malcolmhaslam.recipebook.util.JwtUtil.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import com.malcolmhaslam.recipebook.service.AuthenticationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public LoginResponseDto authenticateUser(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        System.out.println("Logging in: " + loginRequestDto.getEmail() + " with pass: " + loginRequestDto.getPassword());

        TokenResponse tokenResponse;
        try {
            tokenResponse = authenticationService.authenticate(loginRequestDto);
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Or return a proper response
        }
        UserDto userDto = authenticationService.getUserByEmail(loginRequestDto.getEmail());
        return new LoginResponseDto(tokenResponse.getToken(), tokenResponse.getExpirationTime(), userDto);
    }
}