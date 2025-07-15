package com.example.devicerepairmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.devicerepairmanagement.entity.DeviceType;
import com.example.devicerepairmanagement.repository.DeviceTypeRepository;

@Service
public class DeviceTypeService {
    @Autowired
    private DeviceTypeRepository deviceTypeRepository;
    
    public List<DeviceType> findAll() {
        return deviceTypeRepository.findAll();
    }

    public DeviceType save(DeviceType deviceType) {
        return deviceTypeRepository.save(deviceType);
    }

    public Optional<DeviceType> findById(Long id) {
        return deviceTypeRepository.findById(id);
    }

    public void delete(Long id) {
        deviceTypeRepository.deleteById(id);
    }
}
