package com.geekbrains.spring.web.services;

import com.geekbrains.spring.web.auth.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.entities.Product;
import com.geekbrains.spring.web.dto.ProductDto;
import com.geekbrains.spring.web.repositories.ProductRepository;
import com.geekbrains.spring.web.repositories.specifications.ProductsSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;


    public Page<Product> find(Integer minPrice, Integer maxPrice, String partTitle, String categoryTitle, Integer page) {
        Specification<Product> spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ProductsSpecifications.priceGreaterThanOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductsSpecifications.priceLessThanOrEqualsThan(maxPrice));
        }
        if (partTitle != null) {
            spec = spec.and(ProductsSpecifications.titleLike(partTitle));
        }
        if (categoryTitle != null) {
            spec = spec.and(ProductsSpecifications.categoryTitleIs(categoryTitle));
        }
        return productRepository.findAll(spec, PageRequest.of(page - 1, 10));
    }


    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product update(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Невозможно обновить продукт, не найден в базе, id: " + productDto.getId()));
        product.setPrice(productDto.getPrice());
        product.setTitle(productDto.getTitle());
        return product;
    }

    //For SOAP
    public static final Function<Product, com.geekbrains.spring.web.soap.products.Product> functionEntityToSoap = pe -> {
        com.geekbrains.spring.web.soap.products.Product p = new com.geekbrains.spring.web.soap.products.Product();
        p.setId(pe.getId());
        p.setTitle(pe.getTitle());
        p.setPrice(pe.getPrice());
        return p;
    };

    public List<com.geekbrains.spring.web.soap.products.Product> getAllProducts() {
        return productRepository.findAll().stream().map(functionEntityToSoap).collect(Collectors.toList());
    }

    public com.geekbrains.spring.web.soap.products.Product getById(Long id) {
        return productRepository.findById(id).map(functionEntityToSoap).get();
    }
}

