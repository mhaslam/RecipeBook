package com.malcolmhaslam.recipebook.repository;

import com.malcolmhaslam.recipebook.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
