package com.geekbrains.spring.web.controllers;

import com.geekbrains.spring.web.cart.Cart;
import com.geekbrains.spring.web.converters.ProductConverter;
import com.geekbrains.spring.web.entities.Product;
import com.geekbrains.spring.web.dto.ProductDto;
import com.geekbrains.spring.web.exceptions.ResourceNotFoundedException;
import com.geekbrains.spring.web.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final ProductService productService;
    private final ProductConverter productConverter;
    private final Cart cart;


    @GetMapping
    public List<ProductDto> getAllProductsInCart() {
        return cart.getCartList();
    }

    @GetMapping("/add")
    public ProductDto addProductToCartById(@RequestParam Long id) {
        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundedException("Product not found, id: " + id));
        ProductDto productDto = productConverter.entityToDto(product);
        cart.addProductToCart(productDto);
        return productDto;
    }

    @GetMapping("/delete")
    public ProductDto removeProductFromCartById(@RequestParam Long id) {
        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundedException("Product not found, id: " + id));
        ProductDto productDto = productConverter.entityToDto(product);
        cart.removeProductFromCart(productDto);
        return productDto;
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@RequestBody ProductDto productDto) {
        cart.removeProductFromCart(productDto);
    }

    @PutMapping("/cart")
    public ProductDto addProductToCart(@RequestBody ProductDto productDto) {
        cart.addProductToCart(productDto);
        return productDto;
    }
}
