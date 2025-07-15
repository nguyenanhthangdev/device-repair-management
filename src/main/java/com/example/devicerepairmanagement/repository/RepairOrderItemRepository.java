package com.example.devicerepairmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.devicerepairmanagement.entity.RepairOrderItem;

@Repository
public interface RepairOrderItemRepository extends JpaRepository<RepairOrderItem ,Long> {
    
}