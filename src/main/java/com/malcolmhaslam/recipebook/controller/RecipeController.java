package com.malcolmhaslam.recipebook.controller;

import com.malcolmhaslam.recipebook.dto.RecipeDto;
import com.malcolmhaslam.recipebook.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api/{customerId}/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @PostMapping
    public ResponseEntity<RecipeDto> createRecipe(@PathVariable Long customerId, @RequestBody RecipeDto recipeDto, Principal principal ) {
        String updatedBy = (principal != null) ? principal.getName() : null;
        RecipeDto createdRecipe = recipeService.createRecipe(customerId, recipeDto,updatedBy);
        return new ResponseEntity<>(createdRecipe, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable Long customerId, @PathVariable Long id) {
        RecipeDto recipeDto = recipeService.getRecipe(customerId, id);
        return new ResponseEntity<>(recipeDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Set<RecipeDto>> getAllRecipes(@PathVariable Long customerId) {
        Set<RecipeDto> recipes = recipeService.getAllRecipes(customerId);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }
}

