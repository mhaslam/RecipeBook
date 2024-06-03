package com.malcolmhaslam.recipebook.repository;

import com.malcolmhaslam.recipebook.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
}
