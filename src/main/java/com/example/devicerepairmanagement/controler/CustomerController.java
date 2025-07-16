package com.example.devicerepairmanagement.controler;

import java.util.List;
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

import com.example.devicerepairmanagement.dto.CustomerDTO;
import com.example.devicerepairmanagement.entity.Customer;
import com.example.devicerepairmanagement.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<CustomerDTO> getAllCustomer() {
        return customerService.findAll().stream()
                .map(customer ->  new CustomerDTO(
                                        customer.getId(), 
                                        customer.getName(), 
                                        customer.getPhone(), 
                                        customer.getAddress())).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<?> addCustomer(@Valid @RequestBody CustomerDTO customerDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors()
                                    .stream()
                                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setPhone(customerDTO.getPhone());
        customer.setAddress(customerDTO.getAddress());
        Customer savedCustomer = customerService.save(customer);

        CustomerDTO customerDTO2 = new CustomerDTO(savedCustomer.getId(), 
                                                    savedCustomer.getName(), 
                                                    savedCustomer.getPhone(),
                                                    savedCustomer.getAddress());

        return ResponseEntity.status(HttpStatus.CREATED).body(customerDTO2);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody CustomerDTO customerDTO, BindingResult bindingResult, 
                                            @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors()
                                                .stream()
                                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                                .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        return customerService.findById(id)
                                .map(customer -> {
                                    customer.setName(customerDTO.getName());
                                    customer.setPhone(customerDTO.getPhone());
                                    customer.setAddress(customerDTO.getAddress());

                                    Customer savedCustomer = customerService.save(customer);

                                    CustomerDTO customerDTO2 = new CustomerDTO(savedCustomer.getId(), 
                                                                                savedCustomer.getName(), 
                                                                                savedCustomer.getPhone(), 
                                                                                savedCustomer.getAddress());

                                    return ResponseEntity.ok().body(customerDTO2);
                                })
                                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        return customerService.findById(id)
                                .map(customer -> {
                                    customerService.delete(id);
                                    return ResponseEntity.noContent().build();
                                })
                                .orElse(ResponseEntity.notFound().build());
    }
}
