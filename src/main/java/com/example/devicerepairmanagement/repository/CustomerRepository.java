package com.example.devicerepairmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.devicerepairmanagement.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer ,Long> {
    
}
