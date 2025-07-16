package com.example.devicerepairmanagement.controler;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.devicerepairmanagement.dto.RepairOrderItemDTO;
import com.example.devicerepairmanagement.entity.Device;
import com.example.devicerepairmanagement.entity.RepairOrder;
import com.example.devicerepairmanagement.entity.RepairOrderItem;
import com.example.devicerepairmanagement.service.DeviceService;
import com.example.devicerepairmanagement.service.RepairOrderItemService;
import com.example.devicerepairmanagement.service.RepairOrderService;

import jakarta.validation.Valid;

@RestController
public class RepairOrderItemController {
    @Autowired
    private RepairOrderItemService repairOrderItemService;
    @Autowired
    private RepairOrderService repairOrderService;
    @Autowired
    private DeviceService deviceService;

    @GetMapping("/api/repair-items")
    public List<RepairOrderItemDTO> getAllRepairOrderItem() {
        return repairOrderItemService.findAll()
                                        .stream()
                                        .map(
                                            repairOrderItem -> new RepairOrderItemDTO(
                                                repairOrderItem.getId(),
                                                repairOrderItem.getRepairOrder().getId(),
                                                repairOrderItem.getDevice().getId(),
                                                repairOrderItem.getDevice().getName(),
                                                repairOrderItem.getNote(),
                                                repairOrderItem.getCost()
                                        )).collect(Collectors.toList());
    }

    @GetMapping("/api/repair-orders/{orderId}/items")
    public ResponseEntity<?> getItemByOrder(@PathVariable Long orderId) {
        Optional<RepairOrder> repairOrderOptional = repairOrderService.findById(orderId);

        if (repairOrderOptional.isPresent()) {
            List<RepairOrderItemDTO> items = repairOrderOptional.get().getItems()
                                                                      .stream()
                                                                      .map(
                                                                        item -> new RepairOrderItemDTO(
                                                                            item.getId(),
                                                                            item.getRepairOrder().getId(),
                                                                            item.getDevice().getId(),
                                                                            item.getDevice().getName(),
                                                                            item.getNote(),
                                                                            item.getCost())).collect(Collectors.toList());
            return ResponseEntity.ok().body(items);               
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/api/repair-order-items")
    public ResponseEntity<?> createRepairOrderItem(@Valid @RequestBody RepairOrderItemDTO repairOrderItemDTO,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors()
                                    .stream()
                                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        Optional<Device> deviceOptional = deviceService.findById(repairOrderItemDTO.getDeviceId());
        Optional<RepairOrder> repairOrderOptional = repairOrderService.findById(repairOrderItemDTO.getRepairOrderId());

        if (deviceOptional.isEmpty() || repairOrderOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("RepairOrder or Device not found.");
        }

        RepairOrderItem RepairOrderItem = new RepairOrderItem();
        RepairOrderItem.setRepairOrder(repairOrderOptional.get());
        RepairOrderItem.setDevice(deviceOptional.get());
        RepairOrderItem.setNote(repairOrderItemDTO.getNote());
        RepairOrderItem.setCost(repairOrderItemDTO.getCost());
        
        RepairOrderItem savedRepairOrderItem = repairOrderItemService.save(RepairOrderItem);
        RepairOrderItemDTO repairOrderItemDTO2 = new RepairOrderItemDTO(
                                                                    savedRepairOrderItem.getId(),
                                                                    savedRepairOrderItem.getRepairOrder().getId(),
                                                                    savedRepairOrderItem.getDevice().getId(),
                                                                    savedRepairOrderItem.getDevice().getName(),
                                                                    savedRepairOrderItem.getNote(),
                                                                    savedRepairOrderItem.getCost()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(repairOrderItemDTO2);
    }

    @PutMapping("/api/repair-order-items/{id}")
    public ResponseEntity<?> updateRepairOrderItems(@Valid @RequestBody RepairOrderItemDTO repairOrderItemDTO,
                                                    BindingResult bindingResult, @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors()
                                    .stream()
                                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }   

        Optional<RepairOrder> repairOrderOptional = repairOrderService.findById(repairOrderItemDTO.getRepairOrderId());
        Optional<Device> deviceOptional = deviceService.findById(repairOrderItemDTO.getDeviceId());

        if (repairOrderOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Repair order not found with ID: " + repairOrderItemDTO.getRepairOrderId());
        }

        if (deviceOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Device not found with ID: " + repairOrderItemDTO.getDeviceId());
        }
        
        return repairOrderItemService.findById(id).map(
                                                    repairOrderItem -> {
                                                        repairOrderService.findById(repairOrderItemDTO.getRepairOrderId()).ifPresent(repairOrderItem::setRepairOrder);
                                                        deviceService.findById(repairOrderItemDTO.getDeviceId()).ifPresent(repairOrderItem::setDevice);
                                                        repairOrderItem.setNote(repairOrderItemDTO.getNote());
                                                        repairOrderItem.setCost(repairOrderItemDTO.getCost());

                                                        RepairOrderItem savedRepairOrderItem = repairOrderItemService.save(repairOrderItem);
                                                        RepairOrderItemDTO repairOrderItemDTO2 = new RepairOrderItemDTO(
                                                                                                                    savedRepairOrderItem.getId(),
                                                                                                                    savedRepairOrderItem.getRepairOrder().getId(),
                                                                                                                    savedRepairOrderItem.getDevice().getId(),
                                                                                                                    savedRepairOrderItem.getDevice().getName(),
                                                                                                                    savedRepairOrderItem.getNote(),
                                                                                                                    savedRepairOrderItem.getCost()); 
                                                        return ResponseEntity.ok().body(repairOrderItemDTO2);
                                                    }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/api/repair-order-items/{id}")
    public ResponseEntity<?> deleteRepairOrderItems(@PathVariable Long id) {
        return repairOrderItemService.findById(id).map(
                                                    repairOrderItem -> {
                                                        repairOrderItemService.delete(id);
                                                        return ResponseEntity.noContent().build();
                                                    }).orElse(ResponseEntity.notFound().build());      
    }
}
