package com.example.devicerepairmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.devicerepairmanagement.entity.DeviceType;

@Repository
public interface DeviceTypeRepository extends JpaRepository<DeviceType ,Long> {
    
}
