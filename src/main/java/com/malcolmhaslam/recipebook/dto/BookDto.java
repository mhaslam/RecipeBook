package com.malcolmhaslam.recipebook.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class BookDto extends BaseDto {
    private String title;
    private String description;
    private String imageUrl;
    private String color;
    private int recipeCount;
    private List<String> recipes;
}
