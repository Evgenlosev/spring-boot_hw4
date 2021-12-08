package com.geekbrains.spring.web.controllers;

import com.geekbrains.spring.web.entities.Product;
import com.geekbrains.spring.web.entities.ProductDto;
import com.geekbrains.spring.web.exceptions.ResourceNotFoundedException;
import com.geekbrains.spring.web.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

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

        return productService.find(minPrice, maxPrice, titlePart, page).map(p -> new ProductDto(p));
    }


    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.findById(id).orElseThrow(() -> new ResourceNotFoundedException("Product not found, id: " + id));
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        return productService.saveAndFlush(productDto);
    }

    @GetMapping("/change_price")
    public void changePrice(@RequestParam Long productId, @RequestParam Integer delta) {
        productService.changePrice(productId, delta);
    }


    @PostMapping
    public ProductDto addNewProductToRepository(@RequestBody ProductDto productDto){
        productDto.setId(null);
        return productService.saveAndFlush(productDto);
    }
}
