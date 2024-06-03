package com.malcolmhaslam.recipebook.dto;

import lombok.Data;
import java.util.Set;

@Data
public class RecipeDto {
    private Long id;
    private String title;
    private String description;
    private String instructions;
    private String imageUrl;
    private Set<String> tags;
    private Long userId;
    private Set<Long> bookIds;
    private Set<RecipeIngredientDto> ingredients;
}
