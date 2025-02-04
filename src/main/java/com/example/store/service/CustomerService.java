package com.example.store.service;

import com.example.store.dto.customer.CustomerCreateRequestDTO;
import com.example.store.dto.customer.CustomerResponseDTO;
import com.example.store.entity.Customer;
import com.example.store.mapper.CustomerMapper;
import com.example.store.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    /**
     * Method to get all customers in the database
     *
     * @return List of customers
     */
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerMapper.customersToCustomerResponseDTOs(customerRepository.findAll());
    }

    /**
     * Method to find all customers that have substring 'name' in their full name in the database.
     *
     * @param partialName name to search for
     * @return List of customers whose name partially or full matches the partial name
     */
    public List<CustomerResponseDTO> getAllByPartialName(String partialName) {
        return customerMapper.customersToCustomerResponseDTOs(
                customerRepository.findByNameContainingIgnoreCase(partialName));
    }

    /**
     * Method to create a customer in the database
     *
     * @param customerCreateRequestDTO Information needed to create the customer
     * @return Newly created customer in the database
     */
    public CustomerResponseDTO createCustomer(CustomerCreateRequestDTO customerCreateRequestDTO) {
        Customer customer = customerMapper.createCustomerRequestDTOToCustomer(customerCreateRequestDTO);
        return customerMapper.customerToCustomerResponseDTO(customerRepository.save(customer));
    }
}
