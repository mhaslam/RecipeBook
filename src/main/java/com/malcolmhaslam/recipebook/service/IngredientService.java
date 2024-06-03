package com.malcolmhaslam.recipebook.service;

import com.malcolmhaslam.recipebook.dto.IngredientDto;
import com.malcolmhaslam.recipebook.entity.Ingredient;
import com.malcolmhaslam.recipebook.exception.ResourceNotFoundException;
import com.malcolmhaslam.recipebook.repository.IngredientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private ModelMapper modelMapper;

    public IngredientDto createIngredient(IngredientDto ingredientDto) {
        Ingredient ingredient = modelMapper.map(ingredientDto, Ingredient.class);
        ingredient = ingredientRepository.save(ingredient);
        return modelMapper.map(ingredient, IngredientDto.class);
    }

    public IngredientDto getIngredient(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));
        return modelMapper.map(ingredient, IngredientDto.class);
    }

    public Set<IngredientDto> getAllIngredients() {
        return ingredientRepository.findAll().stream()
                .map(ingredient -> modelMapper.map(ingredient, IngredientDto.class))
                .collect(Collectors.toSet());
    }
}
