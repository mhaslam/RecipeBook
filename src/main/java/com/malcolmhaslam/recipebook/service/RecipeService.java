package com.malcolmhaslam.recipebook.service;

import com.malcolmhaslam.recipebook.dto.RecipeDto;
import com.malcolmhaslam.recipebook.entity.*;
import com.malcolmhaslam.recipebook.exception.ResourceNotFoundException;
import com.malcolmhaslam.recipebook.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public RecipeDto createRecipe(Long customerId, RecipeDto recipeDto, String createdBy) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Recipe recipe = modelMapper.map(recipeDto, Recipe.class);

        User user = userRepository.findById(recipeDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Set<Book> books = recipeDto.getBookIds().stream()
                .map(bookId -> bookRepository.findById(bookId)
                        .orElseThrow(() -> new ResourceNotFoundException("Book not found")))
                .collect(Collectors.toSet());
        recipe.setBooks(books);

        recipe.setCustomer(customer);

        if (createdBy != null) {
            User createdByUser = userRepository.findByEmail(createdBy)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            recipe.setCreatedBy(createdByUser);
            recipe.setUpdatedBy(createdByUser);
        }

        recipe = recipeRepository.save(recipe);

        final Recipe savedRecipe = recipe;

        Set<RecipeIngredient> recipeIngredients = recipeDto.getIngredients().stream()
                .map(dto -> {
                    Ingredient ingredient = ingredientRepository.findById(dto.getIngredientId())
                            .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));
                    RecipeIngredient recipeIngredient = new RecipeIngredient();
                    recipeIngredient.setRecipe(savedRecipe);
                    recipeIngredient.setIngredient(ingredient);
                    recipeIngredient.setQuantity(dto.getQuantity());
                    recipeIngredient.setUnit(dto.getUnit());
                    return recipeIngredient;
                })
                .collect(Collectors.toSet());
        recipe.setIngredients(recipeIngredients);

        recipeRepository.save(recipe);
        return modelMapper.map(recipe, RecipeDto.class);
    }


    public RecipeDto getRecipe(Long customerId, Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found"));

        if (!recipe.getCustomer().getId().equals(customerId)) {
            throw new ResourceNotFoundException("Recipe not found for this customer");
        }

        return modelMapper.map(recipe, RecipeDto.class);
    }

    public Set<RecipeDto> getAllRecipes(Long customerId) {
        return recipeRepository.findAll().stream()
                .filter(recipe -> recipe.getCustomer().getId().equals(customerId))
                .map(recipe -> modelMapper.map(recipe, RecipeDto.class))
                .collect(Collectors.toSet());
    }
}
