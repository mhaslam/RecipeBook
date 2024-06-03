package com.malcolmhaslam.recipebook.service;

import com.malcolmhaslam.recipebook.dto.BookDto;
import com.malcolmhaslam.recipebook.entity.Book;
import com.malcolmhaslam.recipebook.exception.ResourceNotFoundException;
import com.malcolmhaslam.recipebook.repository.BookRepository;
import com.malcolmhaslam.recipebook.repository.RecipeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public BookDto createBook(BookDto bookDto) {
        Book book = modelMapper.map(bookDto, Book.class);
        book = bookRepository.save(book);
        return modelMapper.map(book, BookDto.class);
    }

    public BookDto getBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        return modelMapper.map(book, BookDto.class);
    }

    public Set<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toSet());
    }
}
