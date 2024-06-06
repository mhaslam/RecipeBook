package com.malcolmhaslam.recipebook.service;

import com.malcolmhaslam.recipebook.dto.BookDto;
import com.malcolmhaslam.recipebook.entity.Book;
import com.malcolmhaslam.recipebook.entity.Customer;
import com.malcolmhaslam.recipebook.exception.ResourceNotFoundException;
import com.malcolmhaslam.recipebook.repository.BookRepository;
import com.malcolmhaslam.recipebook.repository.CustomerRepository;
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
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public BookDto createBook(Long customerId, BookDto bookDto) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Book book = modelMapper.map(bookDto, Book.class);
        book.setCustomer(customer);
        book = bookRepository.save(book);
        System.out.println(book.toString());
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
