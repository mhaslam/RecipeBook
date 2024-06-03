package com.malcolmhaslam.recipebook.repository;

import com.malcolmhaslam.recipebook.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
