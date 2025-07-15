package com.example.devicerepairmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.devicerepairmanagement.repository.RepairOrderItemRepository;

@Service
public class RepairOrderItemService {
    @Autowired
    private RepairOrderItemRepository repairOrderItemRepository;


}
