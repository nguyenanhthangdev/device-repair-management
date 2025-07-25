package com.example.devicerepairmanagement.controler;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.devicerepairmanagement.dto.DeviceDTO;
import com.example.devicerepairmanagement.entity.Device;
import com.example.devicerepairmanagement.entity.DeviceType;
import com.example.devicerepairmanagement.service.DeviceService;
import com.example.devicerepairmanagement.service.DeviceTypeService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
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
                        type.getType() != null ? type.getType().getName() : null,
                        type.getDescription(),
                        type.getReceivedDate(),
                        type.getStatus()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<?> saveDevice(@Valid @RequestBody DeviceDTO deviceDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

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
                savedDevice.getType() != null ? savedDevice.getType().getName() : null,
                savedDevice.getDescription(),
                savedDevice.getReceivedDate(),
                savedDevice.getStatus());

        return ResponseEntity.status(HttpStatus.CREATED).body(deviceDTO2);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDeviceById(@PathVariable Long id) {
        return deviceService.findById(id)
                .map(device -> {
                    DeviceDTO dto = new DeviceDTO(
                            device.getId(),
                            device.getName(),
                            device.getType() != null ? device.getType().getId() : null,
                            device.getType() != null ? device.getType().getName() : null,
                            device.getDescription(),
                            device.getReceivedDate(),
                            device.getStatus());
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDevice(@PathVariable Long id, @Valid @RequestBody DeviceDTO deviceDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        return deviceService.findById(id)
                .map(device -> {
                    device.setName(deviceDTO.getName());
                    device.setDescription(deviceDTO.getDescription());
                    device.setReceivedDate(deviceDTO.getReceivedDate());
                    device.setStatus(deviceDTO.getStatus());

                    if (deviceDTO.getTypeId() != null) {
                        deviceTypeService.findById(deviceDTO.getTypeId()).ifPresent(device::setType);
                    }

                    Device savedDevice = deviceService.save(device);

                    DeviceDTO deviceDTO2 = new DeviceDTO(savedDevice.getId(),
                            savedDevice.getName(),
                            savedDevice.getType().getId(),
                            savedDevice.getType().getName(),
                            savedDevice.getDescription(),
                            savedDevice.getReceivedDate(),
                            savedDevice.getStatus());

                    return ResponseEntity.ok(deviceDTO2);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDevice(@PathVariable Long id) {
        return deviceService.findById(id)
                .map(device -> {
                    deviceService.delete(id);
                    return ResponseEntity.ok("Xóa thiết bị thành công");
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy thiết bị"));
    }
}
