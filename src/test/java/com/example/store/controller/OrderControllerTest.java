package com.example.store.controller;

import com.example.store.dto.order.OrderCreateRequestDTO;
import com.example.store.dto.order.OrderResponseDTO;
import com.example.store.service.OrderService;

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
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Test
    void getAllOrders_ShouldReturnListOfOrders() throws Exception {
        List<OrderResponseDTO> orders = List.of(new OrderResponseDTO());
        when(orderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(get("/order"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getAllOrders_ShouldReturnNoContent() throws Exception {
        when(orderService.getAllOrders()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/order")).andExpect(status().isNoContent());
    }

    @Test
    void getOrderById_ShouldReturnOrder() throws Exception {
        Long orderId = 1L;
        OrderResponseDTO order = new OrderResponseDTO();
        when(orderService.getOrderById(orderId)).thenReturn(Optional.of(order));

        mockMvc.perform(get("/order/{id}", orderId)).andExpect(status().isOk());
    }

    @Test
    void getOrderById_ShouldReturnNoContentFound() throws Exception {
        Long orderId = 1L;
        when(orderService.getOrderById(any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/order/{id}", orderId)).andExpect(status().isNoContent());
    }

    @Test
    void createOrder_ShouldReturnCreated() throws Exception {
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        when(orderService.createOrder(any(OrderCreateRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/order").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isCreated());
    }

    @Test
    void createOrder_ShouldReturnInternalServerError() throws Exception {
        when(orderService.createOrder(any(OrderCreateRequestDTO.class))).thenReturn(null);

        mockMvc.perform(post("/order").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isInternalServerError());
    }
}
