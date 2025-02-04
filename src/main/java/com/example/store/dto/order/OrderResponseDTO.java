package com.example.store.dto.order;

import lombok.Data;

import java.util.List;

/** Order Response Dto is used in the rest controller to standardize the response for all endpoints. */
@Data
public class OrderResponseDTO {
    private Long id;
    private String description;
    private OrderCustomerDTO customer;
    private List<OrderProductDTO> products;
}
