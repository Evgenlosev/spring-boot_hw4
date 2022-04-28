package com.geekbrains.spring.web.cart.integrations;

import com.geekbrains.spring.web.api.carts.CartDto;
import com.geekbrains.spring.web.api.core.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductsServiceIntegration {
//    private final RestTemplate restTemplate;
    private final WebClient coreServiceWebClient;

    public Optional<ProductDto> findById(Long id) {
        Optional<ProductDto> product = Optional.ofNullable(coreServiceWebClient.get()
                .uri("/api/v1/products/" + id)
//                .header("username", username)
                .retrieve()
                .bodyToMono(ProductDto.class)
                .block());
        return product;
    }

//    @Value("${integrations.core-service.url}")
//    private String productServiceUrl;
//
//    public Optional<ProductDto> findById(Long id) {
//        ProductDto productDto = restTemplate.getForObject(productServiceUrl + "/api/v1/products/" + id, ProductDto.class);
//        return Optional.ofNullable(productDto);
//    }
}
