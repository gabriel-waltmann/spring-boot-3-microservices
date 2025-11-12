package com.gabriel.microservices.order.service;

import com.gabriel.microservices.order.client.InventoryClient;
import com.gabriel.microservices.order.dto.OrderRequest;
import com.gabriel.microservices.order.model.Order;
import com.gabriel.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequest orderRequest) {
        var productIsInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if (!productIsInStock) {
            throw new RuntimeException("Product with SKU code " + orderRequest.skuCode() + " is not in stock.");
        }

        // Create Order entity from OrderRequest
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setSkuCode(orderRequest.skuCode());
        order.setPrice(orderRequest.price());
        order.setQuantity(orderRequest.quantity());

        orderRepository.save(order);
    }
}