package com.malcolmhaslam.recipebook.service;

import com.malcolmhaslam.recipebook.dto.LoginRequestDto;
import com.malcolmhaslam.recipebook.dto.UserDto;
import com.malcolmhaslam.recipebook.util.JwtUtil;
import com.malcolmhaslam.recipebook.util.JwtUtil.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    public TokenResponse authenticate(LoginRequestDto loginRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword()
                )
        );
        return jwtUtil.generateToken(loginRequest.getEmail());
    }
    public UserDto getUserByEmail(String email) {
//        UserDto userDto = userService.getUserByEmail(email);
        return userService.getUserByEmail(email);
    }
}
