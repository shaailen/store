package com.example.store.dto.customer;

import lombok.Data;

import java.util.List;

/** Customer Response Dto is used in the rest controller to standardize the response for all endpoints. */
@Data
public class CustomerResponseDTO {
    private Long id;
    private String name;
    private List<CustomerOrderDTO> orders;
}
