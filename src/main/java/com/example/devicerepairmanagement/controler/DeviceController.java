package com.example.devicerepairmanagement.controler;

import java.lang.foreign.Linker.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.devicerepairmanagement.dto.DeviceDTO;
import com.example.devicerepairmanagement.entity.Device;
import com.example.devicerepairmanagement.entity.DeviceType;
import com.example.devicerepairmanagement.service.DeviceService;
import com.example.devicerepairmanagement.service.DeviceTypeService;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceTypeService deviceTypeService;

    @GetMapping
    public List<DeviceDTO> getAllDevice() {
        return deviceService.findAll().stream()
                .map(type -> new DeviceDTO(
                                type.getId(), 
                                type.getName(), 
                                type.getType() != null ? type.getType().getId() : null,
                                type.getType().getName(),
                                type.getDescription(),
                                type.getReceivedDate(), 
                                type.getStatus())).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<DeviceDTO> saveDevice(@RequestBody DeviceDTO deviceDTO) {
        Device device = new Device();
        device.setName(deviceDTO.getName());
        device.setDescription(deviceDTO.getDescription());
        device.setReceivedDate(deviceDTO.getReceivedDate());
        device.setStatus(deviceDTO.getStatus());
        if (deviceDTO.getTypeId() != null) {
            Optional<DeviceType> deviceTypeOptional = deviceTypeService.findById(deviceDTO.getTypeId());
            if (deviceTypeOptional.isPresent()) {
                device.setType(deviceTypeOptional.get());
            }
        }
        Device savedDevice = deviceService.save(device);

        DeviceDTO deviceDTO2 = new DeviceDTO(
            savedDevice.getId(),
            savedDevice.getName(),
            savedDevice.getType() != null ? savedDevice.getType().getId() : null,
            savedDevice.getType() != null ? savedDevice.getType().getName(): null,
            savedDevice.getDescription(),
            savedDevice.getReceivedDate(),
            savedDevice.getStatus()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(deviceDTO2);
    }
}
