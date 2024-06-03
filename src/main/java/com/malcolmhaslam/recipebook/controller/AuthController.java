package com.malcolmhaslam.recipebook.controller;

import com.malcolmhaslam.recipebook.dto.LoginRequestDto;
import com.malcolmhaslam.recipebook.dto.LoginResponseDto;
import com.malcolmhaslam.recipebook.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public LoginResponseDto authenticateUser(@RequestBody LoginRequestDto loginRequestDto) {
        System.out.println("Logging in: " + loginRequestDto.getEmail() + " with pass: " + loginRequestDto.getPassword());

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getEmail(),
                            loginRequestDto.getPassword()
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Or return a proper response
        }

        System.out.println("Authenticated: " + authentication.isAuthenticated());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtil.generateToken(loginRequestDto.getEmail());
        return new LoginResponseDto(jwt);
    }
}
