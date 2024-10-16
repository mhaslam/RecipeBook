package com.malcolmhaslam.recipebook.service;

import com.malcolmhaslam.recipebook.dto.RecipeDto;
import com.malcolmhaslam.recipebook.entity.*;
import com.malcolmhaslam.recipebook.exception.ResourceNotFoundException;
import com.malcolmhaslam.recipebook.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import java.util.List;
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
    private TagRepository tagRepository;

    @Autowired
    private ModelMapper modelMapper;

    public RecipeDto createRecipe(Long customerId, RecipeDto recipeDto, String createdBy) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        // Sanitize instructions HTML
        String safeInstructions = Jsoup.clean(recipeDto.getInstructions(), Safelist.basic());
        recipeDto.setInstructions(safeInstructions);

        Recipe recipe = modelMapper.map(recipeDto, Recipe.class);

        Set<Book> books = recipeDto.getBookIds().stream()
                .map(bookId -> bookRepository.findById(bookId)
                        .orElseThrow(() -> new ResourceNotFoundException("Book not found")))
                .collect(Collectors.toSet());
        recipe.setBooks(books);

        Set<Tag> tags = recipeDto.getTags().stream()
                .map(tagTitle -> tagRepository.findByTitle(tagTitle)
                        .orElseGet(() -> tagRepository.save(new Tag(tagTitle))))
                .collect(Collectors.toSet());
        recipe.setTags(tags);

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

        // Clear existing ingredients and add the new ones
        savedRecipe.getIngredients().clear();
        savedRecipe.getIngredients().addAll(recipeIngredients);

        recipeRepository.save(savedRecipe);
        return modelMapper.map(savedRecipe, RecipeDto.class);
    }

    public RecipeDto getRecipe(Long customerId, Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found"));

        if (!recipe.getCustomer().getId().equals(customerId)) {
            throw new ResourceNotFoundException("Recipe not found for this customer");
        }

        return modelMapper.map(recipe, RecipeDto.class);
    }

    public Page<RecipeDto> getAllRecipes(Long customerId, Pageable pageable) {
        Page<Recipe> recipesPage = recipeRepository.findByCustomerId(customerId, pageable);
        return recipesPage.map(recipe -> modelMapper.map(recipe, RecipeDto.class));
    }

    public Page<RecipeDto> getRecipesByTitle(Long customerId, String title, Pageable pageable) {
        Page<Recipe> recipesPage = recipeRepository.findByCustomerIdAndTitleContaining(customerId, title, pageable);
        return recipesPage.map(recipe -> modelMapper.map(recipe, RecipeDto.class));
    }

    public Page<RecipeDto> getRecipesByTags(Long customerId, List<String> tags, Pageable pageable) {
        Page<Recipe> recipesPage = recipeRepository.findByCustomerIdAndTags_TitleIn(customerId, tags, pageable);
        return recipesPage.map(recipe -> modelMapper.map(recipe, RecipeDto.class));
    }

    public Page<RecipeDto> getRecipesByTitleAndTags(Long customerId, String title, List<String> tags, Pageable pageable) {
        Page<Recipe> recipesPage = recipeRepository.findByCustomerIdAndTitleContainingAndTags_TitleIn(customerId, title, tags, pageable);
        return recipesPage.map(recipe -> modelMapper.map(recipe, RecipeDto.class));
    }

    public Page<RecipeDto> getRecipesByBook(Long customerId, Long bookId, Pageable pageable) {
        Page<Recipe> recipesPage = recipeRepository.findByCustomerIdAndBookId(customerId, bookId, pageable);
        return recipesPage.map(recipe -> modelMapper.map(recipe, RecipeDto.class));
    }
}
