package com.malcolmhaslam.recipebook.service;

import com.malcolmhaslam.recipebook.dto.BookDto;
import com.malcolmhaslam.recipebook.dto.UserDto;
import com.malcolmhaslam.recipebook.entity.Book;
import com.malcolmhaslam.recipebook.entity.Customer;
import com.malcolmhaslam.recipebook.entity.Recipe;
import com.malcolmhaslam.recipebook.entity.User;
import com.malcolmhaslam.recipebook.exception.ResourceNotFoundException;
import com.malcolmhaslam.recipebook.repository.BookRepository;
import com.malcolmhaslam.recipebook.repository.CustomerRepository;
import com.malcolmhaslam.recipebook.repository.RecipeRepository;
import com.malcolmhaslam.recipebook.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    public List<BookDto> getBooksWithRecipeCount(Long customerId) {
        List<Book> books = bookRepository.findByCustomerId(customerId);
        return books.stream().map(book -> {
            BookDto bookDto = new BookDto();
            bookDto.setId(book.getId());
            bookDto.setTitle(book.getTitle());
            bookDto.setDescription(book.getDescription());
            bookDto.setImageUrl(book.getImageUrl());
            bookDto.setRecipeCount(book.getRecipes().size());
            return bookDto;
        }).collect(Collectors.toList());
    }

    public BookDto getBookWithRecipeTitles(Long customerId, Long bookId) {
        Book book = bookRepository.findByIdAndCustomerId(bookId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setDescription(book.getDescription());
        bookDto.setImageUrl(book.getImageUrl());
        bookDto.setRecipes(book.getRecipes().stream().map(Recipe::getTitle).collect(Collectors.toList()));
        return bookDto;
    }


    public BookDto createBook(Long customerId, BookDto bookDto, String createdBy) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Book book = modelMapper.map(bookDto, Book.class);
        book.setCustomer(customer);
        book = bookRepository.save(book);

        if (createdBy != null) {
            User createdByUser = userRepository.findByEmail(createdBy)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            book.setCreatedBy(createdByUser);
            book.setUpdatedBy(createdByUser);
        }

        return modelMapper.map(book, BookDto.class);
    }

    public BookDto updateBook(Long bookId, BookDto bookDto, String updatedBy) {
        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        // Map fields from DTO to existing entity except for the id
        ModelMapper modelMapperUpdate = new ModelMapper();
        modelMapperUpdate.getConfiguration().setPropertyCondition(context -> !context.getMapping().getLastDestinationProperty().getName().equals("id"));
        modelMapperUpdate.map(bookDto, existingBook);

        if (updatedBy != null) {
            User updatedByUser = userRepository.findByEmail(updatedBy)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            existingBook.setUpdatedBy(updatedByUser);
        }
        System.out.println("UPDATE BOOK");
        System.out.println(existingBook.getColor());
        System.out.println("UPDATE BOOK");
        existingBook = bookRepository.save(existingBook);

        return modelMapper.map(existingBook, BookDto.class);
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

    public void deleteBook(Long customerId, Long bookId) {
        Book book = bookRepository.findByIdAndCustomerId(bookId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        bookRepository.delete(book);
    }
}
