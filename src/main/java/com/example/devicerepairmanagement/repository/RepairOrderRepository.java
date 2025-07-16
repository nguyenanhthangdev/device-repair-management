package com.example.devicerepairmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.devicerepairmanagement.entity.RepairOrder;

@Repository
public interface RepairOrderRepository extends JpaRepository<RepairOrder ,Long> {
    
    boolean existsByCode(String code);

}
