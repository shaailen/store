package com.example.store.service;

import com.example.store.dto.product.ProductCreateRequestDTO;
import com.example.store.dto.product.ProductResponseDto;
import com.example.store.entity.Product;
import com.example.store.mapper.ProductMapper;
import com.example.store.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {}

    @Test
    void testGetAllProducts() {
        List<Product> products = Arrays.asList(new Product(), new Product());
        List<ProductResponseDto> productDtos = Arrays.asList(new ProductResponseDto(), new ProductResponseDto());

        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.productsToProductResponseDTOs(products)).thenReturn(productDtos);

        List<ProductResponseDto> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById() {
        Long id = 1L;
        Product product = new Product();
        ProductResponseDto productDto = new ProductResponseDto();

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productMapper.productToProductResponseDTO(product)).thenReturn(productDto);

        Optional<ProductResponseDto> result = productService.getProductById(id);

        assertNotNull(result);
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void testGetProductById_NotContentFound() {
        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        Optional<ProductResponseDto> result = productService.getProductById(id);

        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void testCreateProduct() {
        ProductCreateRequestDTO requestDTO = new ProductCreateRequestDTO();
        Product product = new Product();
        ProductResponseDto productDto = new ProductResponseDto();

        when(productMapper.productCreateRequestDTOToProduct(requestDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.productToProductResponseDTO(product)).thenReturn(productDto);

        ProductResponseDto result = productService.createProduct(requestDTO);

        assertNotNull(result);
        verify(productRepository, times(1)).save(product);
    }
}
