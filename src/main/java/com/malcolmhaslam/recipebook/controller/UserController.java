package com.malcolmhaslam.recipebook.controller;

import com.malcolmhaslam.recipebook.dto.UserDto;
import com.malcolmhaslam.recipebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api/{customerId}/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto, Principal principal) {
        String createdBy = (principal != null) ? principal.getName() : null;
        UserDto createdUser = userService.createUser(userDto, createdBy);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto, Principal principal) {
        String updatedBy = (principal != null) ? principal.getName() : null;
        UserDto updatedUser = userService.updateUser(id, userDto, updatedBy);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Long customerId, @PathVariable Long userId) {
        return userService.getUser(customerId, userId);
    }

    @GetMapping
    public Set<UserDto> getAllUsers(@PathVariable Long customerId) {
        return userService.getAllUsers(customerId);
    }
}
