package com.example.store.mapper;

import com.example.store.dto.order.OrderCreateRequestDTO;
import com.example.store.dto.order.OrderResponseDTO;
import com.example.store.entity.Customer;
import com.example.store.entity.Order;
import com.example.store.entity.Product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponseDTO orderToOrderDTO(Order order);

    List<OrderResponseDTO> ordersToOrderDTOs(List<Order> orders);

    @Mapping(target = "id", ignore = true)
    Order orderCreateRequestDTOAndCustomerToOrder(
            OrderCreateRequestDTO orderCreateRequestDTO, Customer customer, List<Product> products);
}
