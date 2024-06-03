package com.malcolmhaslam.recipebook.repository;

import com.malcolmhaslam.recipebook.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
