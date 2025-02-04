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

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    /**
     * Method to retrieve all the orders stored in the database.
     *
     * @return List of orders from the database
     */
    public List<OrderResponseDTO> getAllOrders() {
        return orderMapper.ordersToOrderDTOs(orderRepository.findAll());
    }

    /**
     * Method to find an order based on an id
     *
     * @param id id to search for
     * @return Order details found in the database
     */
    public Optional<OrderResponseDTO> getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(orderMapper::orderToOrderDTO);
    }

    /**
     * Method to create an order for a customer. Both the customer and product much exist in the database for an order
     * to be created.
     *
     * @param orderCreateRequestDTO details of the order to be created
     * @return newly created order
     */
    public OrderResponseDTO createOrder(OrderCreateRequestDTO orderCreateRequestDTO) {
        Optional<Customer> customer = customerRepository.findById(orderCreateRequestDTO.getCustomerId());
        List<Product> productList = productRepository.findAllById(orderCreateRequestDTO.getProductList());
        Order order = null;
        if (customer.isPresent() && !productList.isEmpty()) {
            order = orderMapper.orderCreateRequestDTOAndCustomerToOrder(
                    orderCreateRequestDTO, customer.get(), productList);
        }

        return order != null ? orderMapper.orderToOrderDTO(orderRepository.save(order)) : null;
    }
}
