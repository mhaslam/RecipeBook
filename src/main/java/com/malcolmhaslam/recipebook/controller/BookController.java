package com.malcolmhaslam.recipebook.controller;

import com.malcolmhaslam.recipebook.dto.BookDto;
import com.malcolmhaslam.recipebook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api/{customerId}/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public BookDto createBook(@PathVariable Long customerId, @RequestBody BookDto bookDto) {
        return bookService.createBook(customerId, bookDto);
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    @GetMapping
    public Set<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }
}
