package com.malcolmhaslam.recipebook.service;

import com.malcolmhaslam.recipebook.dto.UserDto;
import com.malcolmhaslam.recipebook.entity.Customer;
import com.malcolmhaslam.recipebook.entity.User;
import com.malcolmhaslam.recipebook.exception.ResourceNotFoundException;
import com.malcolmhaslam.recipebook.repository.CustomerRepository;
import com.malcolmhaslam.recipebook.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, CustomerRepository customerRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto createUser(UserDto userDto, String createdBy) {
        System.out.println("User DTO: " + userDto);

        User user = modelMapper.map(userDto, User.class);
        System.out.println("Mapped User: " + user);

        Customer customer = customerRepository.findById(userDto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        user.setCustomer(customer);

        System.out.println("Encoding password: " + userDto.getPassword());
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // Encode the password

        if (createdBy != null) {
            User createdByUser = userRepository.findByEmail(createdBy)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            user.setCreatedBy(createdByUser);
            user.setUpdatedBy(createdByUser);
        }

        user = userRepository.save(user);
        System.out.println("Saved User: " + user);

        return modelMapper.map(user, UserDto.class);
    }

    public UserDto getUser(Long customerId, Long userId) {
        User user = userRepository.findByIdAndCustomerId(userId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        System.out.println("User Found id: "+user.getId());
        return modelMapper.map(user, UserDto.class);
    }

    public Set<UserDto> getAllUsers(Long customerId) {
        Set<User> users = userRepository.findAllByCustomerId(customerId);
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toSet());
    }

    public UserDto updateUser(Long userId, UserDto userDto, String updatedBy) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        modelMapper.map(userDto, existingUser); // Update existing user fields with DTO values

        if (updatedBy != null) {
            User updatedByUser = userRepository.findByEmail(updatedBy)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            existingUser.setUpdatedBy(updatedByUser);
        }

        existingUser = userRepository.save(existingUser);
        return modelMapper.map(existingUser, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>()); // Customize authorities as needed
    }
}
