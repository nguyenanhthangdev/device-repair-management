package com.example.devicerepairmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.devicerepairmanagement.repository.RepairOrderRepository;

@Service
public class RepairOrderService {
    @Autowired
    private RepairOrderRepository repairOrderRepository;
    
}
