package com.malcolmhaslam.recipebook.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "books")
public class Book extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToMany(mappedBy = "books")
    private Set<Recipe> recipes;

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
