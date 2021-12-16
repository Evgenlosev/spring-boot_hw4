package com.geekbrains.spring.web.controllers;

import com.geekbrains.spring.web.cart.Cart;
import com.geekbrains.spring.web.converters.ProductConverter;
import com.geekbrains.spring.web.entities.Product;
import com.geekbrains.spring.web.entities.ProductDto;
import com.geekbrains.spring.web.exceptions.ResourceNotFoundedException;
import com.geekbrains.spring.web.services.ProductService;
import com.geekbrains.spring.web.validators.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;
    private final ProductValidator productValidator;
    private final Cart cart;


    @GetMapping
    public Page<ProductDto> getAllProducts(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "min_price", required = false) Integer minPrice,
            @RequestParam(name = "max_price", required = false) Integer maxPrice,
            @RequestParam(name = "title_part", required = false) String titlePart
    ) {
        if (page < 1) {
            page = 1;
        }
        return productService.find(minPrice, maxPrice, titlePart, page).map(p -> productConverter.entityToDto(p));
    }


    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundedException("Product not found, id: " + id));
        return productConverter.entityToDto(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productService.update(productDto);
        return productConverter.entityToDto(product);
    }

    @PostMapping
    public ProductDto saveNewProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productConverter.dtoToEntity(productDto);
        product.setId(null);
        product = productService.save(product);
        return productConverter.entityToDto(product);
    }

    @GetMapping("/cart")
    public List<ProductDto> getAllProductsInCart() {
        return cart.getCartList();
    }

    @GetMapping("/cart/add")
    public ProductDto addProductToCartById(@RequestParam Long id) {
        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundedException("Product not found, id: " + id));
        ProductDto productDto = productConverter.entityToDto(product);
        cart.addProductToCart(productDto);
        return productDto;
    }

    @GetMapping("/cart/delete")
    public ProductDto removeProductFromCartById(@RequestParam Long id) {
        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundedException("Product not found, id: " + id));
        ProductDto productDto = productConverter.entityToDto(product);
        cart.removeProductFromCart(productDto);
        return productDto;
    }

    @DeleteMapping("/cart/{id}")
    public void deleteProduct(@RequestBody ProductDto productDto) {
        cart.removeProductFromCart(productDto);
    }

    @PutMapping("/cart")
    public ProductDto addProductToCart(@RequestBody ProductDto productDto) {
        cart.addProductToCart(productDto);
        return productDto;
    }
}
