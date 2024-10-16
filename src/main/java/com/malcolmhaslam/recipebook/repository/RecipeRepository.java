package com.malcolmhaslam.recipebook.repository;

import com.malcolmhaslam.recipebook.entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Page<Recipe> findByCustomerId(Long customerId, Pageable pageable);
    Page<Recipe> findByCustomerIdAndTitleContaining(Long customerId, String title, Pageable pageable);

    @Query("SELECT r FROM Recipe r JOIN r.tags t WHERE r.customer.id = :customerId AND t.title IN :tags")
    Page<Recipe> findByCustomerIdAndTags_TitleIn(@Param("customerId") Long customerId, @Param("tags") List<String> tags, Pageable pageable);

    @Query("SELECT r FROM Recipe r JOIN r.tags t WHERE r.customer.id = :customerId AND r.title LIKE %:title% AND t.title IN :tags")
    Page<Recipe> findByCustomerIdAndTitleContainingAndTags_TitleIn(@Param("customerId") Long customerId, @Param("title") String title, @Param("tags") List<String> tags, Pageable pageable);

    @Query("SELECT r FROM Recipe r JOIN r.books b WHERE b.id = :bookId AND r.customer.id = :customerId")
    Page<Recipe> findByCustomerIdAndBookId(@Param("customerId") Long customerId, @Param("bookId") Long bookId, Pageable pageable);
}
