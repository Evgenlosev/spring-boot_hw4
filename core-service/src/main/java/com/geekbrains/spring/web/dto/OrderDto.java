package com.geekbrains.spring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private String userName;
    private Integer totalPrice;
    private String address;
    private String phone;
    private List<OrderItemDto> orderItems;
}
