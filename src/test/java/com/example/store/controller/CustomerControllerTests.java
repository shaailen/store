package com.example.store.controller;

import com.example.store.dto.customer.CustomerCreateRequestDTO;
import com.example.store.dto.customer.CustomerResponseDTO;
import com.example.store.service.CustomerService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
class CustomerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @Test
    void getAllCustomers_ReturnListOfCustomers() throws Exception {
        List<CustomerResponseDTO> customers = List.of(new CustomerResponseDTO());
        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getAllCustomers_ReturnNoContent() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/customer")).andExpect(status().isNoContent());
    }

    @Test
    void getCustomerByPartialName_ReturnCustomers() throws Exception {
        String name = "Shailen";
        List<CustomerResponseDTO> customers = List.of(new CustomerResponseDTO());
        when(customerService.getAllByPartialName(name)).thenReturn(customers);

        mockMvc.perform(get("/customer/name").param("name", name)).andExpect(status().isOk());
    }

    @Test
    void getCustomerByPartialName_ReturnNoContent() throws Exception {
        String name = "Shailen";
        when(customerService.getAllByPartialName(name)).thenReturn(null);

        mockMvc.perform(get("/customer/name").param("name", name)).andExpect(status().isNoContent());
    }

    @Test
    void createCustomer_ReturnCreated() throws Exception {
        CustomerResponseDTO responseDTO = new CustomerResponseDTO();
        when(customerService.createCustomer(any(CustomerCreateRequestDTO.class)))
                .thenReturn(responseDTO);

        mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isCreated());
    }

    @Test
    void createCustomer_ReturnInternalServerError() throws Exception {
        when(customerService.createCustomer(any(CustomerCreateRequestDTO.class)))
                .thenReturn(null);

        mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isInternalServerError());
    }
}
