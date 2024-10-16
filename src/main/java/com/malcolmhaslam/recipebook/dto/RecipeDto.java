package com.malcolmhaslam.recipebook.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class RecipeDto extends BaseDto {
    private String title;
    private String description;
    private String instructions;
    private String imageUrl;
    private Set<String> tags;
    private Set<Long> bookIds;
    private Set<RecipeIngredientDto> ingredients;
}
