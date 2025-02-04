package com.example.store.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Product Create Request Dto is used in the POST method to specify the details to create a new product. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequestDTO {
    private String description;
}
