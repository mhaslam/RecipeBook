package com.malcolmhaslam.recipebook.dto;

import lombok.Data;
import java.util.Set;

@Data
public class BookDto {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private Set<Long> recipeIds;
}
