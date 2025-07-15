package com.example.devicerepairmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.devicerepairmanagement.entity.Device;
import com.example.devicerepairmanagement.repository.DeviceRepository;

@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;
    
    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    public Device save(Device device) {
        return deviceRepository.save(device);
    }

    public Optional<Device> findById(Long id) {
        return deviceRepository.findById(id);
    }

    public void delete(Long id) {
        deviceRepository.deleteById(id);
    }
}
