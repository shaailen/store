package com.example.store.service;

import com.example.store.dto.product.ProductCreateRequestDTO;
import com.example.store.dto.product.ProductResponseDto;
import com.example.store.entity.Product;
import com.example.store.mapper.ProductMapper;
import com.example.store.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    /**
     * Method to retrieve all the products stored in the database.
     *
     * @return List of products from the database
     */
    public List<ProductResponseDto> getAllProducts() {
        return productMapper.productsToProductResponseDTOs(productRepository.findAll());
    }

    /**
     * Method to find a product based on an id
     *
     * @param id id to search for
     * @return Product details found in the database
     */
    public Optional<ProductResponseDto> getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(productMapper::productToProductResponseDTO);
    }

    /**
     * Method to create a new product
     *
     * @param productCreateRequestDTO product details to store in the database
     * @return newly created product
     */
    public ProductResponseDto createProduct(ProductCreateRequestDTO productCreateRequestDTO) {
        Product product = productMapper.productCreateRequestDTOToProduct(productCreateRequestDTO);
        return productMapper.productToProductResponseDTO(productRepository.save(product));
    }
}
