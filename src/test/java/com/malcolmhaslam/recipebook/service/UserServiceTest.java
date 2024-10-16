package com.malcolmhaslam.recipebook.service;

import com.malcolmhaslam.recipebook.entity.User;
import com.malcolmhaslam.recipebook.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
//
//    @InjectMocks
//    private UserService userService;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testFindUserById() {
//        Long userId = 1L;
//        User mockUser = new User();
//        mockUser.setId(userId);
//        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
//
//        Optional<User> user = userService.findUserById(userId);
//
//        assertTrue(user.isPresent());
//        assertEquals(userId, user.get().getId());
//        verify(userRepository, times(1)).findById(userId);
//    }
//
//    @Test
//    public void testFindUserById_NotFound() {
//        Long userId = 1L;
//        when(userRepository.findById(userId)).thenReturn(Optional.empty());
//
//        Optional<User> user = userService.findUserById(userId);
//
//        assertFalse(user.isPresent());
//        verify(userRepository, times(1)).findById(userId);
//    }

    // Add more tests for other methods
}
