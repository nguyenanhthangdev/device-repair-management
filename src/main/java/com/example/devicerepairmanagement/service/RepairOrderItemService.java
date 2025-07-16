package com.example.devicerepairmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.devicerepairmanagement.entity.RepairOrderItem;
import com.example.devicerepairmanagement.repository.RepairOrderItemRepository;

@Service
public class RepairOrderItemService {
    @Autowired
    private RepairOrderItemRepository repairOrderItemRepository;

    public List<RepairOrderItem> findAll() {
        return repairOrderItemRepository.findAll();
    }

    public Optional<RepairOrderItem> findById(Long id) {
        return repairOrderItemRepository.findById(id);
    }

    public RepairOrderItem save(RepairOrderItem repairOrderItem) {
        return repairOrderItemRepository.save(repairOrderItem);
    }

    public void delete(Long id) {
        repairOrderItemRepository.deleteById(id);
    }
}
