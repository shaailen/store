package com.example.store.controller;

import com.example.store.dto.order.OrderCreateRequestDTO;
import com.example.store.dto.order.OrderResponseDTO;
import com.example.store.service.OrderService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<OrderResponseDTO> orderResponseDTOS = orderService.getAllOrders();
        return orderResponseDTOS.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(orderResponseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        Optional<OrderResponseDTO> orderDTO = orderService.getOrderById(id);
        return orderDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderCreateRequestDTO orderCreateRequestDTO) {
        OrderResponseDTO orderResponseDTO = orderService.createOrder(orderCreateRequestDTO);
        return orderResponseDTO == null
                ? ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
                : ResponseEntity.status(HttpStatus.CREATED).body(orderResponseDTO);
    }
}
