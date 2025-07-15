package com.example.devicerepairmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.devicerepairmanagement.entity.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device ,Long> {
    
}