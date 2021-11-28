package com.geekbrains.spring.web.controllers;

import com.geekbrains.spring.web.data.Product;
import com.geekbrains.spring.web.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {
    private ProductService productService;

    public MainController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/delete/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping("/products/change_price")
    public void changePrice(@RequestParam Long productId, @RequestParam Integer delta) {
        productService.changePrice(productId, delta);
    }
}
