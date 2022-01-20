package com.geekbrains.spring.web.services;

import com.geekbrains.spring.web.dto.Cart;
import com.geekbrains.spring.web.dto.OrderItemDto;
import com.geekbrains.spring.web.entities.Order;
import com.geekbrains.spring.web.entities.OrderItem;
import com.geekbrains.spring.web.exceptions.ResourceNotFoundedException;
import com.geekbrains.spring.web.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final OrderItemService orderItemService;
    private final ProductService productService;

    @Transactional
    public Long createNewOrder(Cart cart, String userName) {
        Order order = new Order();
        order.setUser(userService.findByUsername(userName).orElseThrow(() -> new ResourceNotFoundedException(String.format("Пользователь с именем %s не найден", userName))));
        order.setTotalPrice(cart.getTotalPrice());
        orderRepository.save(order);
        List<OrderItem> items = new ArrayList<>();
        for (OrderItemDto o : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setPricePerProduct(o.getPrice());
            orderItem.setOrder(order);
            orderItem.setProduct(productService.findById(o.getProductId()).orElseThrow(() -> new ResourceNotFoundedException("Продукт не найден")));
            orderItem.setQuantity(o.getQuantity());
            orderItem.setPrice(o.getQuantity() * o.getPricePerProduct());
            items.add(orderItem);
        }
        order.setOrderItems(orderItemService.saveAll(items));
        return order.getId();
    }
}
