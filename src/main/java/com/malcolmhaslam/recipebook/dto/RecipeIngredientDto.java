package com.malcolmhaslam.recipebook.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RecipeIngredientDto extends BaseDto {
    private Long ingredientId;
    private Double quantity;
    private String unit;
}
