package com.malcolmhaslam.recipebook.controller;

import com.malcolmhaslam.recipebook.dto.RecipeDto;
import com.malcolmhaslam.recipebook.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/{customerId}/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @PostMapping
    public ResponseEntity<RecipeDto> createRecipe(@PathVariable Long customerId, @RequestBody RecipeDto recipeDto, Principal principal ) {
        String updatedBy = (principal != null) ? principal.getName() : null;
        RecipeDto createdRecipe = recipeService.createRecipe(customerId, recipeDto, updatedBy);
        return new ResponseEntity<>(createdRecipe, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable Long customerId, @PathVariable Long id) {
        System.out.println("Loading recipe "+id);
        RecipeDto recipeDto = recipeService.getRecipe(customerId, id);
        return new ResponseEntity<>(recipeDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<RecipeDto>> getAllRecipes(
            @PathVariable Long customerId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) Long bookId,
            Pageable pageable) {

        Page<RecipeDto> recipes;
        if (title != null && tags != null) {
            recipes = recipeService.getRecipesByTitleAndTags(customerId, title, tags, pageable);
        } else if (title != null) {
            recipes = recipeService.getRecipesByTitle(customerId, title, pageable);
        } else if (tags != null) {
            recipes = recipeService.getRecipesByTags(customerId, tags, pageable);
        } else if (bookId != null) {
            recipes = recipeService.getRecipesByBook(customerId, bookId, pageable);
        } else {
            recipes = recipeService.getAllRecipes(customerId, pageable);
        }
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }
}
