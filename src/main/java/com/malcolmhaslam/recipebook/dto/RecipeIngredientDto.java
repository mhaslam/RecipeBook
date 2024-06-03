package com.malcolmhaslam.recipebook.dto;

import lombok.Data;

@Data
public class RecipeIngredientDto {
    private Long ingredientId;
    private Double quantity;
    private String unit;
}
