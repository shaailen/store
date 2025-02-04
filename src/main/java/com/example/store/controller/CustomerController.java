package com.example.store.controller;

import com.example.store.dto.customer.CustomerCreateRequestDTO;
import com.example.store.dto.customer.CustomerResponseDTO;
import com.example.store.service.CustomerService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        List<CustomerResponseDTO> customerResponseDTOS = customerService.getAllCustomers();

        return customerResponseDTOS.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(customerResponseDTOS);
    }

    @GetMapping("/name")
    public ResponseEntity<List<CustomerResponseDTO>> getCustomerByPartialName(@RequestParam String name) {
        List<CustomerResponseDTO> customerResponseDTOS = customerService.getAllByPartialName(name);
        return customerResponseDTOS == null
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(customerResponseDTOS);
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(
            @RequestBody CustomerCreateRequestDTO customerCreateRequestDTO) {
        CustomerResponseDTO customerResponseDTO = customerService.createCustomer(customerCreateRequestDTO);
        return customerResponseDTO == null
                ? ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
                : ResponseEntity.status(HttpStatus.CREATED).body(customerResponseDTO);
    }
}
