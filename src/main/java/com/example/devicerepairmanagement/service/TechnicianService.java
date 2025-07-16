package com.example.devicerepairmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.devicerepairmanagement.entity.Technician;
import com.example.devicerepairmanagement.repository.TechnicianRepository;

@Service
public class TechnicianService {
    @Autowired
    private TechnicianRepository technicianRepository;
    
    public List<Technician> findAll() {
        return technicianRepository.findAll();
    }

    public Technician save(Technician technician) {
        return technicianRepository.save(technician);
    }

    public Optional<Technician> findById(Long id) {
        return technicianRepository.findById(id);
    }

    public void delete(Long id) {
        technicianRepository.deleteById(id);
    }
}
