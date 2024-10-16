package com.malcolmhaslam.recipebook.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class IngredientDto extends BaseDto {
    private String name;
    private String imageUrl;
}
