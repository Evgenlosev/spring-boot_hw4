package com.geekbrains.spring.web.controllers;

import com.geekbrains.spring.web.api.core.ProductDto;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.converters.ProductConverter;
import com.geekbrains.spring.web.entities.Product;
import com.geekbrains.spring.web.services.ProductService;
import com.geekbrains.spring.web.validators.ProductValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Продукты", description = "Методы работы с продуктами")
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;
    private final ProductValidator productValidator;


    @GetMapping
    @Operation(summary = "Запрос на получение страницы продуктов",
                responses = {
                    @ApiResponse(description = "Успешный ответ", responseCode = "200",
                                content = @Content(schema = @Schema(implementation = Page.class))
                                )
                })
    public Page<ProductDto> getAllProducts(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "min_price", required = false) Integer minPrice,
            @RequestParam(name = "max_price", required = false) Integer maxPrice,
            @RequestParam(name = "title_part", required = false) String titlePart,
            @RequestParam(name = "category_title", required = false) String categoryTitle
    ) {
        if (page < 1) {
            page = 1;
        }
        return productService.find(minPrice, maxPrice, titlePart, categoryTitle, page).map(p -> productConverter.entityToDto(p));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Запрос на получение продукта по id",
            responses = {
                    @ApiResponse(description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            })
    public ProductDto getProductById(
            @PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long id
    ) {
        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));
        return productConverter.entityToDto(product);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Запрос на удаление продукта по id")
    public void deleteProductById(
            @PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long id
    ) {
        productService.deleteById(id);
    }

    @PutMapping
    @Operation(summary = "Запрос на обновление продукта",
            responses = {
                    @ApiResponse(description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            })
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productConverter.dtoToEntity(productDto);
        product = productService.save(product);
        return productConverter.entityToDto(product);
    }

    @PostMapping
    @Operation(summary = "Запрос на добавление нового продукта",
            responses = {
                    @ApiResponse(description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            })
    public ProductDto saveNewProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productService.update(productDto);
        return productConverter.entityToDto(product);
    }

}
