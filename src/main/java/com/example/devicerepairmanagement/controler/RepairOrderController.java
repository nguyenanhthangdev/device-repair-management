package com.example.devicerepairmanagement.controler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

import com.example.devicerepairmanagement.dto.RepairOrderDTO;
import com.example.devicerepairmanagement.entity.RepairOrder;
import com.example.devicerepairmanagement.service.CustomerService;
import com.example.devicerepairmanagement.service.RepairOrderService;
import com.example.devicerepairmanagement.service.TechnicianService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/repair-orders")
public class RepairOrderController {
    @Autowired
    private RepairOrderService repairOrderService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TechnicianService technicianService;

    @GetMapping
    public List<RepairOrderDTO> getAllRepairOrder() {
        return repairOrderService.findAll()
                                    .stream()
                                    .map(repairOrder -> new RepairOrderDTO(
                                        repairOrder.getId(),
                                        repairOrder.getCode(),
                                        repairOrder.getRepairDate(),
                                        repairOrder.getStatus(),
                                        repairOrder.getCost(),
                                        repairOrder.getCustomer() != null ? repairOrder.getCustomer().getId() : null,
                                        repairOrder.getTechnician() != null ? repairOrder.getTechnician().getId() : null
                                    )).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<?> saveRepairOrder(@Valid @RequestBody RepairOrderDTO repairOrderDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors()
                                                .stream()
                                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                                .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
        RepairOrder repairOrder = new RepairOrder();
        // Gọi hàm generate từ service
        String newCode = repairOrderService.generateUniqueCode();
        repairOrder.setCode(newCode);
        repairOrder.setRepairDate(repairOrderDTO.getRepairDate());
        repairOrder.setStatus(repairOrderDTO.getStatus());
        repairOrder.setCost(repairOrderDTO.getCost());
        customerService.findById(repairOrderDTO.getCustomerId()).ifPresent(repairOrder::setCustomer);
        technicianService.findById(repairOrderDTO.getTechnicianId()).ifPresent(repairOrder::setTechnician);

        RepairOrder savedRepairOrder = repairOrderService.save(repairOrder);

        RepairOrderDTO repairOrderDTO2 = new RepairOrderDTO(
                                                savedRepairOrder.getId(),
                                                savedRepairOrder.getCode(),
                                                savedRepairOrder.getRepairDate(),
                                                savedRepairOrder.getStatus(),
                                                savedRepairOrder.getCost(),
                                                savedRepairOrder.getCustomer() != null ? savedRepairOrder.getCustomer().getId() : null,
                                                savedRepairOrder.getTechnician() != null ? savedRepairOrder.getTechnician().getId() : null
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(repairOrderDTO2);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRepairOrder(@Valid @RequestBody RepairOrderDTO repairOrderDTO,
                                                BindingResult bindingResult, @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors()
                                                .stream()
                                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                                .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
        
        return repairOrderService.findById(id)
                                    .map(repairOrder -> {
                                        repairOrder.setRepairDate(repairOrderDTO.getRepairDate());
                                        repairOrder.setStatus(repairOrderDTO.getStatus());
                                        repairOrder.setCost(repairOrderDTO.getCost());
                                        customerService.findById(repairOrderDTO.getCustomerId()).ifPresent(repairOrder::setCustomer);
                                        technicianService.findById(repairOrderDTO.getTechnicianId()).ifPresent(repairOrder::setTechnician);

                                        RepairOrder savedRepairOrder = repairOrderService.save(repairOrder);

                                        RepairOrderDTO repairOrderDTO2 = new RepairOrderDTO(
                                                                                savedRepairOrder.getId(),
                                                                                savedRepairOrder.getCode(),
                                                                                savedRepairOrder.getRepairDate(),
                                                                                savedRepairOrder.getStatus(),
                                                                                savedRepairOrder.getCost(),
                                                                                savedRepairOrder.getCustomer() != null ? savedRepairOrder.getCustomer().getId() : null,
                                                                                savedRepairOrder.getTechnician() != null ? savedRepairOrder.getTechnician().getId() : null
                                        );

                                        return ResponseEntity.ok().body(repairOrderDTO2);
                                    })
                                    .orElse(ResponseEntity.notFound().build());
    }      

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRepairOrder(@PathVariable Long id) {
        return repairOrderService.findById(id)
                                    .map(
                                        repairOrder -> {
                                            repairOrderService.delete(id);
                                            return ResponseEntity.noContent().build();
                                        }
                                    )
                                    .orElse(ResponseEntity.notFound().build());
    }
}
