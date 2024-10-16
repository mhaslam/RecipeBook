package com.malcolmhaslam.recipebook.repository;

import com.malcolmhaslam.recipebook.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByCustomerId(Long customerId);
    Optional<Book> findByIdAndCustomerId(Long id, Long customerId);
}
