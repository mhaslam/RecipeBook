package com.malcolmhaslam.recipebook.service;

import com.malcolmhaslam.recipebook.dto.CustomerDto;
import com.malcolmhaslam.recipebook.entity.Customer;
import com.malcolmhaslam.recipebook.exception.ResourceNotFoundException;
import com.malcolmhaslam.recipebook.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CustomerDto getCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return modelMapper.map(customer, CustomerDto.class);
    }

    public Set<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customer -> modelMapper.map(customer, CustomerDto.class))
                .collect(Collectors.toSet());
    }
}
