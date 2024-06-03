package com.malcolmhaslam.recipebook.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Column(nullable = false)
    private String password;

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
