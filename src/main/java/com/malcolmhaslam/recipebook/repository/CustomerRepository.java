package com.malcolmhaslam.recipebook.repository;

import com.malcolmhaslam.recipebook.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
