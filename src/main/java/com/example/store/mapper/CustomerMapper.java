package com.example.store.mapper;

import com.example.store.dto.customer.CustomerCreateRequestDTO;
import com.example.store.dto.customer.CustomerResponseDTO;
import com.example.store.entity.Customer;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerResponseDTO customerToCustomerResponseDTO(Customer customer);

    List<CustomerResponseDTO> customersToCustomerResponseDTOs(List<Customer> customer);

    Customer createCustomerRequestDTOToCustomer(CustomerCreateRequestDTO customerCreateRequestDTO);
}
