package com.malcolmhaslam.recipebook.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @Getter @Setter
    private Customer customer;

    @ManyToMany(mappedBy = "books")
    @Getter @Setter
    private Set<Recipe> recipes;
}
