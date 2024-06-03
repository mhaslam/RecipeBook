package com.malcolmhaslam.recipebook.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    @Getter @Setter
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    @Getter @Setter
    private Ingredient ingredient;

    @Getter @Setter
    @Column(nullable = false)
    private Double quantity;

    @Getter @Setter
    @Column(nullable = false)
    private String unit;
}
