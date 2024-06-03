package com.malcolmhaslam.recipebook.controller;

import com.malcolmhaslam.recipebook.dto.IngredientDto;
import com.malcolmhaslam.recipebook.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api/{customerId}/ingredients")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @PostMapping
    public IngredientDto createIngredient(@RequestBody IngredientDto ingredientDto) {
        return ingredientService.createIngredient(ingredientDto);
    }

    @GetMapping("/{id}")
    public IngredientDto getIngredient(@PathVariable Long id) {
        return ingredientService.getIngredient(id);
    }

    @GetMapping
    public Set<IngredientDto> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }
}
