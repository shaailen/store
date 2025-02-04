package com.example.store.service;

import com.example.store.dto.order.OrderCreateRequestDTO;
import com.example.store.dto.order.OrderResponseDTO;
import com.example.store.entity.Customer;
import com.example.store.entity.Order;
import com.example.store.entity.Product;
import com.example.store.mapper.OrderMapper;
import com.example.store.repository.CustomerRepository;
import com.example.store.repository.OrderRepository;
import com.example.store.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private OrderResponseDTO orderResponseDTO;
    private OrderCreateRequestDTO orderCreateRequestDTO;
    private Customer customer;
    private Product product;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1L);

        orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId(1L);

        customer = new Customer();
        customer.setId(1L);

        product = new Product();
        product.setId(1L);

        orderCreateRequestDTO = new OrderCreateRequestDTO();
        orderCreateRequestDTO.setCustomerId(1L);
        orderCreateRequestDTO.setProductList(Arrays.asList(1L));
    }

    @Test
    void testGetAllOrders() {
        when(orderRepository.findAll()).thenReturn(List.of(order));
        when(orderMapper.ordersToOrderDTOs(List.of(order))).thenReturn(List.of(orderResponseDTO));

        List<OrderResponseDTO> result = orderService.getAllOrders();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testGetOrderById() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderMapper.orderToOrderDTO(order)).thenReturn(orderResponseDTO);

        Optional<OrderResponseDTO> result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals(1L, result.get().getId());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testGetOrderById_NoContentFound() {
        when(orderRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<OrderResponseDTO> result = orderService.getOrderById(2L);

        assertTrue(result.isEmpty());
        verify(orderRepository, times(1)).findById(2L);
    }

    @Test
    void testCreateOrder_Success() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(productRepository.findAllById(List.of(1L))).thenReturn(List.of(product));
        when(orderMapper.orderCreateRequestDTOAndCustomerToOrder(orderCreateRequestDTO, customer, List.of(product)))
                .thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.orderToOrderDTO(order)).thenReturn(orderResponseDTO);

        OrderResponseDTO result = orderService.createOrder(orderCreateRequestDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testCreateOrder_Failure_InvalidCustomerOrProducts() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        when(productRepository.findAllById(List.of(1L))).thenReturn(List.of());

        OrderResponseDTO result = orderService.createOrder(orderCreateRequestDTO);

        assertNull(result);
        verify(orderRepository, never()).save(any());
    }
}
