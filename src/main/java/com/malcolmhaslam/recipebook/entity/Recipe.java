package com.malcolmhaslam.recipebook.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Column(nullable = false)
    private String description;

    @Setter
    @Column(nullable = false)
    private String instructions;

    @Setter
    @Column(nullable = false)
    private String imageUrl;

    @Setter
    @ElementCollection
    private Set<String> tags;

    @ManyToMany
    @JoinTable(
            name = "recipe_books",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @Setter
    private Set<Book> books;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private Set<RecipeIngredient> ingredients;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @Setter
    private Customer customer;

    @Column(name = "created_at", updatable = false)
    @Setter
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by", updatable = false)
    @Setter
    private User createdBy;

    @Column(name = "updated_at")
    @Setter
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    @Setter
    private User updatedBy;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        // createdBy should be set in the service layer
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        // updatedBy should be set in the service layer
    }

}
