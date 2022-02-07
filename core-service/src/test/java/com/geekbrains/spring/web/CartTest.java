package com.geekbrains.spring.web;

import com.geekbrains.spring.web.dto.Cart;
import com.geekbrains.spring.web.dto.OrderItemDto;
import com.geekbrains.spring.web.entities.Category;
import com.geekbrains.spring.web.entities.Product;
import com.geekbrains.spring.web.services.CartService;
import com.geekbrains.spring.web.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CartTest {
    @Autowired
    private CartService cartService;

    @MockBean
    private ProductService productService;


    @BeforeEach
    public void initCart() {
        cartService.clearCart("test_cart");
    }

    @Test
    public void addToCartTest() {
        Product product = new Product();
        product.setId(1L);
        product.setPrice(100);
        product.setTitle("Bread");

        Category category = new Category();
        product.setCategory(category);

        Mockito.doReturn(Optional.of(product)).when(productService).findById(1L);
        cartService.addToCart("test_cart", 1L);
        cartService.addToCart("test_cart", 1L);
        cartService.addToCart("test_cart", 1L);

        Mockito.verify(productService, Mockito.times(3)).findById(ArgumentMatchers.eq(1L));
        Assertions.assertEquals(1, cartService.getCurrentCart("test_cart").getItems().size());
    }

    @Test
    public void mergeTest() {
        OrderItemDto orderItemDto1 = new OrderItemDto(1L, "1", 1, 10, 10);
        OrderItemDto orderItemDto2 = new OrderItemDto(2L, "2", 1, 20, 20);
        OrderItemDto orderItemDto3 = new OrderItemDto(3L, "3", 1, 30, 30);

        List<OrderItemDto> guestCartList = new ArrayList<>();
        guestCartList.add(orderItemDto1);
        guestCartList.add(orderItemDto2);

        List<OrderItemDto> userCartList = new ArrayList<>();
        userCartList.add(orderItemDto3);

        Cart guestCart = new Cart();
        guestCart.setItems(guestCartList);
        cartService.updateCart("guest_cart", guestCart);

        Cart userCart = new Cart();
        userCart.setItems(userCartList);
        cartService.updateCart("user_cart", userCart);

        cartService.merge("user_cart", "guest_cart");

        Assertions.assertEquals(0, cartService.getCurrentCart("guest_cart").getItems().size());
        Assertions.assertEquals(3, cartService.getCurrentCart("user_cart").getItems().size());
        Assertions.assertEquals(0, cartService.getCurrentCart("guest_cart").getTotalPrice());
        Assertions.assertEquals(60, cartService.getCurrentCart("user_cart").getTotalPrice());

    }
}
