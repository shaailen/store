package com.example.store.controller;

import com.example.store.dto.product.ProductCreateRequestDTO;
import com.example.store.dto.product.ProductResponseDto;
import com.example.store.service.ProductService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    void getAllProducts_ReturnOk_WhenProductsExist() throws Exception {
        List<ProductResponseDto> products = List.of(new ProductResponseDto(1L, "Product", List.of(1L)));
        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].description").value("Product"))
                .andExpect(jsonPath("$[0].orderIds").value(1));
    }

    @Test
    void getAllProducts_ReturnNoContent_WhenNoProductsExist() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of());

        mockMvc.perform(get("/product")).andExpect(status().isNoContent());
    }

    @Test
    void getProductById_ReturnOk_WhenProductExists() throws Exception {
        ProductResponseDto product = new ProductResponseDto(1L, "Found Product", List.of(1L));
        when(productService.getProductById(1L)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/product/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Found Product"))
                .andExpect(jsonPath("$.orderIds").value(1));
    }

    @Test
    void getProductById_ReturnNotFound_WhenProductDoesNotExist() throws Exception {
        when(productService.getProductById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/product/{id}", 1L)).andExpect(status().isNoContent());
    }

    @Test
    void createProduct_ReturnCreated_WhenProductIsSuccessfullyCreated() throws Exception {
        ProductResponseDto responseDTO = new ProductResponseDto(1L, "New Product", List.of(1L));
        when(productService.createProduct(any(ProductCreateRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"New Product\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("New Product"))
                .andExpect(jsonPath("$.orderIds").value(1));
    }

    @Test
    void createProduct_ReturnInternalServerError_WhenProductCreationFails() throws Exception {
        when(productService.createProduct(any(ProductCreateRequestDTO.class))).thenReturn(null);

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Product\", \"price\":100.0}"))
                .andExpect(status().isInternalServerError());
    }
}
