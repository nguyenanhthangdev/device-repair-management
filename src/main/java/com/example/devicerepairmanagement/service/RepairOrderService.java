package com.example.devicerepairmanagement.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.devicerepairmanagement.entity.RepairOrder;
import com.example.devicerepairmanagement.repository.RepairOrderRepository;

@Service
public class RepairOrderService {
    @Autowired
    private RepairOrderRepository repairOrderRepository;
    
    public List<RepairOrder> findAll() {
        return repairOrderRepository.findAll();
    }

    public RepairOrder save(RepairOrder repairOrder) {
        return repairOrderRepository.save(repairOrder);
    }

    public Optional<RepairOrder> findById(Long id) {
        return repairOrderRepository.findById(id);
    }

    public void delete(Long id) {
        repairOrderRepository.deleteById(id);
    }

    public String generateUniqueCode() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String randomStr;
        String code;

        do {
            randomStr = generateRandomAlphaNumeric(6); // ví dụ: X7A9UH
            code = String.format("RO%s%s", timestamp, randomStr);
        } while (repairOrderRepository.existsByCode(code)); // kiểm tra trùng mã

        return code;
    }

    private String generateRandomAlphaNumeric(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        return sb.toString();
    }
}
