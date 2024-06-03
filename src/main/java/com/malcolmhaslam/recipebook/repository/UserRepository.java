package com.malcolmhaslam.recipebook.repository;

import com.malcolmhaslam.recipebook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    Set<User> findAllByCustomerId(Long customerId);
    Optional<User> findByIdAndCustomerId(Long id, Long customerId);
    Optional<User> findByEmail(String email); // Add this method to find users by email
}
