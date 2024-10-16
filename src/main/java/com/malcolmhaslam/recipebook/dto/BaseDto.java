package com.malcolmhaslam.recipebook.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseDto {
    private Long id;
    private Long customerId;
    private LocalDateTime created;
    private LocalDateTime updated;
    private UserDto createdBy;
    private UserDto updatedBy;
}
