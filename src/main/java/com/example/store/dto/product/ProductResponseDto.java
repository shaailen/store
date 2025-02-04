package com.example.store.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/** Product Response Dto is used in the rest controller to standardize the response for all endpoints. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String description;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Long> orderIds;
}
