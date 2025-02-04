package com.example.store.mapper;

import com.example.store.dto.product.ProductCreateRequestDTO;
import com.example.store.dto.product.ProductResponseDto;
import com.example.store.entity.Order;
import com.example.store.entity.Product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "order", target = "orderIds", qualifiedByName = "productOrderToOrderIdList")
    ProductResponseDto productToProductResponseDTO(Product product);

    List<ProductResponseDto> productsToProductResponseDTOs(List<Product> products);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    Product productCreateRequestDTOToProduct(ProductCreateRequestDTO productCreateRequestDTO);

    /*
     * Helper method that maps the product orders into a list of order ids
     */
    @Named("productOrderToOrderIdList")
    static List<Long> productOrderToOrderIdList(List<Order> orders) {
        return orders == null ? null : orders.stream().map(Order::getId).collect(Collectors.toList());
    }
}
