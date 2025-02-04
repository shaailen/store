package com.example.store.dto.customer;

import lombok.Data;

/**
 * Customer Create Request Dto is used in the POST method to specify the details to create a new customer. Even though
 * it only has one variable in this assessment in reality it would have multiple attributes and is better practice to
 * pass in a dto to the endpoint rather than the entity.
 */
@Data
public class CustomerCreateRequestDTO {
    private String name;
}
