package com.example.devicerepairmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.devicerepairmanagement.entity.Technician;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician ,Long> {
    
}