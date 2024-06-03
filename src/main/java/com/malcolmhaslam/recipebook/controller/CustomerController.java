package com.malcolmhaslam.recipebook.controller;

import com.malcolmhaslam.recipebook.dto.BookDto;
import com.malcolmhaslam.recipebook.dto.CustomerDto;
import com.malcolmhaslam.recipebook.service.BookService;
import com.malcolmhaslam.recipebook.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/{id}")
    public CustomerDto getBook(@PathVariable Long id) {
        return customerService.getCustomer(id);
    }

    @GetMapping
    public Set<CustomerDto> getAllBooks() {
        return customerService.getAllCustomers();
    }
}
