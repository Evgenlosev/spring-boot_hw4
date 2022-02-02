package com.geekbrains.spring.web.services;

import com.geekbrains.spring.web.entities.OrderItem;
import com.geekbrains.spring.web.repositories.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public List<OrderItem> saveAll(List<OrderItem> orderItems) {
        return orderItemRepository.saveAll(orderItems);
    }
}
