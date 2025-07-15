package com.example.devicerepairmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.devicerepairmanagement.repository.TechnicianRepository;

@Service
public class TechnicianService {
    @Autowired
    private TechnicianRepository technicianRepository;
    
}
