package com.example.store.controller;

import com.example.store.dto.product.ProductCreateRequestDTO;
import com.example.store.dto.product.ProductResponseDto;
import com.example.store.service.ProductService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> productResponseDtos = productService.getAllProducts();
        return productResponseDtos.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(productResponseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        Optional<ProductResponseDto> orderDTO = productService.getProductById(id);
        return orderDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductResponseDto> createProduct(
            @RequestBody ProductCreateRequestDTO productCreateRequestDTO) {
        ProductResponseDto productResponseDto = productService.createProduct(productCreateRequestDTO);
        return productResponseDto == null
                ? ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
                : ResponseEntity.status(HttpStatus.CREATED).body(productResponseDto);
    }
}
