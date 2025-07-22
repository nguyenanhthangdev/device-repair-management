package com.example.devicerepairmanagement.controler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.devicerepairmanagement.entity.DeviceType;
import com.example.devicerepairmanagement.service.DeviceTypeService;
import com.example.devicerepairmanagement.dto.DeviceTypeDTO;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/device-types")
public class DeviceTypeController {
    @Autowired
    private DeviceTypeService deviceTypeService;

    @GetMapping
    public List<DeviceTypeDTO> getAllDeviceType() {
        return deviceTypeService.findAll().stream()
                .map(type -> new DeviceTypeDTO(type.getId(), type.getName())).collect(Collectors.toList());
    }

    @PostMapping
    public DeviceType saveDeviceType(@RequestBody DeviceTypeDTO deviceTypeDTO) {
        DeviceType deviceType = new DeviceType();
        deviceType.setName(deviceTypeDTO.getName());
        return deviceTypeService.save(deviceType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceTypeDTO> updateDeviceType(@RequestBody DeviceTypeDTO deviceTypeDTO, @PathVariable Long id) {
        return deviceTypeService.findById(id)
                .map(type -> {
                    type.setName(deviceTypeDTO.getName());
                    deviceTypeService.save(type);    
                    return ResponseEntity.ok(new DeviceTypeDTO(type.getId(), type.getName()));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDeviceType(@PathVariable Long id) {
        return deviceTypeService.findById(id)
                .map(type->{
                    deviceTypeService.delete(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
