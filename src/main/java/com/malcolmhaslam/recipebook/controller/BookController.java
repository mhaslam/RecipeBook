package com.malcolmhaslam.recipebook.controller;

import com.malcolmhaslam.recipebook.dto.BookDto;
import com.malcolmhaslam.recipebook.dto.UserDto;
import com.malcolmhaslam.recipebook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api/{customerId}/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public BookDto createBook(@PathVariable Long customerId, @RequestBody BookDto bookDto,Principal principal) {
        String createdBy = (principal != null) ? principal.getName() : null;
        return bookService.createBook(customerId, bookDto, createdBy);
    }

//    @GetMapping("/{id}")
//    public BookDto getBook(@PathVariable Long id) {
//        return bookService.getBook(id);
//    }

//    @GetMapping
//    public Set<BookDto> getAllBooks() {
//        return bookService.getAllBooks();
//    }


    @GetMapping
    public List<BookDto> getBooks(@PathVariable Long customerId) {
        return bookService.getBooksWithRecipeCount(customerId);
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable Long customerId, @PathVariable("id") Long bookId) {
        return bookService.getBookWithRecipeTitles(customerId, bookId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto, Principal principal) {
        String updatedBy = (principal != null) ? principal.getName() : null;
        System.out.println("UPDATED BY"+updatedBy);
        BookDto updatedBook = bookService.updateBook(id, bookDto, updatedBy);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public List<BookDto>  deleteBook(@PathVariable Long customerId, @PathVariable("id") Long bookId) {
        bookService.deleteBook(customerId, bookId);
        return bookService.getBooksWithRecipeCount(customerId);
    }
}
