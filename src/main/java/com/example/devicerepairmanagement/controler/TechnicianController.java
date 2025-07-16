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

import com.example.devicerepairmanagement.dto.TechnicianDTO;
import com.example.devicerepairmanagement.entity.Technician;
import com.example.devicerepairmanagement.service.TechnicianService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/technicians")
public class TechnicianController {
    @Autowired
    private TechnicianService technicianService;

    @GetMapping
    public List<TechnicianDTO> getAllTechnician() {
        return technicianService.findAll()
                                .stream()
                                .map(technician -> new TechnicianDTO(
                                    technician.getId(),
                                    technician.getName(),
                                    technician.getSpecialization()
                                )).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<?> saveTechnician(@Valid @RequestBody TechnicianDTO technicianDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors()
                                                .stream()
                                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                                .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        Technician technician = new Technician();
        technician.setName(technicianDTO.getName());
        technician.setSpecialization(technicianDTO.getSpecialization());

        Technician savedTechnician = technicianService.save(technician);

        TechnicianDTO technicianDTO2 = new TechnicianDTO(savedTechnician.getId(), 
                                                         savedTechnician.getName(), 
                                                         savedTechnician.getSpecialization());

        return ResponseEntity.status(HttpStatus.CREATED).body(technicianDTO2);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTechnician(@Valid @RequestBody TechnicianDTO technicianDTO,
                                                BindingResult bindingResult, @PathVariable Long id) {

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors()
                                                .stream()
                                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                                .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        return technicianService.findById(id)
                                .map(
                                    techinician -> {
                                        techinician.setName(technicianDTO.getName());
                                        techinician.setSpecialization(technicianDTO.getSpecialization());

                                        Technician savedTechnician = technicianService.save(techinician);

                                        TechnicianDTO technicianDTO2 = new TechnicianDTO(savedTechnician.getId(), 
                                                                                         savedTechnician.getName(), 
                                                                                         savedTechnician.getSpecialization());

                                        return ResponseEntity.ok().body(technicianDTO2);
                                    }
                                )
                                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTechnician(@PathVariable Long id) {
        return technicianService.findById(id)
                                .map(
                                    technician -> {
                                        technicianService.delete(id);
                                        return ResponseEntity.noContent().build();
                                    }
                                )
                                .orElse(ResponseEntity.notFound().build());
    }
}
