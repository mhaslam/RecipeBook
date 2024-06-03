package com.malcolmhaslam.recipebook.controller;

import com.malcolmhaslam.recipebook.dto.UserDto;
import com.malcolmhaslam.recipebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserCreationController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public UserDto createUser(@RequestBody UserDto userDto, Principal principal) {
        System.out.println("Creating User: " + userDto);
        String createdBy = (principal != null) ? principal.getName() : null;
        return userService.createUser(userDto,createdBy);
    }
}
