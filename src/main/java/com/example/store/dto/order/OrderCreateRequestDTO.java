package com.example.store.dto.order;

import lombok.Data;

import java.util.List;

/** Order Create Request Dto is used in the POST method to specify the details to create a new order. */
@Data
public class OrderCreateRequestDTO {
    private String description;
    private Long customerId;
    private List<Long> productList;
}
