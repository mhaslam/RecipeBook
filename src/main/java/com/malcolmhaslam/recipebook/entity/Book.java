package com.malcolmhaslam.recipebook.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Entity
@Table(name = "books")
public class Book {

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
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @Setter
    private Customer customer;

    @ManyToMany(mappedBy = "books")
    @Setter
    private Set<Recipe> recipes;
}
