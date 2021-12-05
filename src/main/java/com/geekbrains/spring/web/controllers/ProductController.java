package com.geekbrains.spring.web.controllers;

import com.geekbrains.spring.web.entities.Product;
import com.geekbrains.spring.web.exceptions.ResourceNotFoundedException;
import com.geekbrains.spring.web.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.findById(id).orElseThrow(() -> new ResourceNotFoundedException("Product not found, id: " + id));
    }

    @GetMapping("/products/delete/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping("/products/change_price")
    public void changePrice(@RequestParam Long productId, @RequestParam Integer delta) {
        productService.changePrice(productId, delta);
    }

    @GetMapping("/products/price_more/{min}")
    public List<Product> findByPriceMore(@PathVariable Integer min) {
        return productService.findByPriceMore(min);
    }

    @GetMapping("/products/price_less/{max}")
    public List<Product> findByPriceLess(@PathVariable Integer max) {
        return productService.findByPriceLess(max);
    }

    @GetMapping("/products/price_between")
    public List<Product> findByPriceBetween(@RequestParam(defaultValue = "0") Integer min, @RequestParam(defaultValue = "999999") Integer max) {
        return productService.findByPriceBetween(min, max);
    }

    @PostMapping("/addProduct")
    @ResponseBody
    public void addNewProductToRepository(@RequestBody Product p){
        productService.saveAndFlush(p);
    }
}
