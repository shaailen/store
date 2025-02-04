package com.example.store.service;

import com.example.store.dto.customer.CustomerCreateRequestDTO;
import com.example.store.dto.customer.CustomerResponseDTO;
import com.example.store.entity.Customer;
import com.example.store.mapper.CustomerMapper;
import com.example.store.repository.CustomerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private CustomerResponseDTO customerResponseDTO;
    private List<Customer> customerList;
    private List<CustomerResponseDTO> customerResponseDTOList;

    @BeforeEach
    public void setUp() {
        // Sample customer data
        customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");

        customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setId(1L);
        customerResponseDTO.setName("John Doe");

        customerList = Arrays.asList(customer);
        customerResponseDTOList = Arrays.asList(customerResponseDTO);
    }

    @Test
    public void testGetAllCustomers() {
        // Arrange
        when(customerRepository.findAll()).thenReturn(customerList);
        when(customerMapper.customersToCustomerResponseDTOs(customerList)).thenReturn(customerResponseDTOList);

        // Act
        List<CustomerResponseDTO> result = customerService.getAllCustomers();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    public void testGetAllByPartialName() {
        // Arrange
        String name = "John";
        when(customerRepository.findByNameContainingIgnoreCase(name)).thenReturn(customerList);
        when(customerMapper.customersToCustomerResponseDTOs(customerList)).thenReturn(customerResponseDTOList);

        // Act
        List<CustomerResponseDTO> result = customerService.getAllByPartialName(name);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    public void testCreateCustomer() {
        // Arrange
        CustomerCreateRequestDTO customerCreateRequestDTO = new CustomerCreateRequestDTO();
        customerCreateRequestDTO.setName("Jane Doe");

        when(customerMapper.createCustomerRequestDTOToCustomer(customerCreateRequestDTO))
                .thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.customerToCustomerResponseDTO(customer)).thenReturn(customerResponseDTO);

        // Act
        CustomerResponseDTO result = customerService.createCustomer(customerCreateRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    public void testGetAllCustomers_empty() {
        // Arrange
        when(customerRepository.findAll()).thenReturn(Arrays.asList());
        when(customerMapper.customersToCustomerResponseDTOs(Arrays.asList())).thenReturn(Arrays.asList());

        // Act
        List<CustomerResponseDTO> result = customerService.getAllCustomers();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllByPartialName_empty() {
        // Arrange
        String name = "Unknown";
        when(customerRepository.findByNameContainingIgnoreCase(name)).thenReturn(Arrays.asList());
        when(customerMapper.customersToCustomerResponseDTOs(Arrays.asList())).thenReturn(Arrays.asList());

        // Act
        List<CustomerResponseDTO> result = customerService.getAllByPartialName(name);

        // Assert
        assertTrue(result.isEmpty());
    }
}
