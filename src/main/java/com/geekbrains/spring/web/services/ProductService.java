package com.geekbrains.spring.web.services;

import com.geekbrains.spring.web.entities.Product;
import com.geekbrains.spring.web.entities.ProductDto;
import com.geekbrains.spring.web.exceptions.ResourceNotFoundedException;
import com.geekbrains.spring.web.repositories.ProductRepository;
import com.geekbrains.spring.web.repositories.specifications.ProductsSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> find(Integer minPrice, Integer maxPrice, String partTitle, Integer page) {
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
        return productRepository.findAll(spec, PageRequest.of(page - 1, 5));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void changePrice(Long productId, Integer delta) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundedException("Unable to change product's price, id: " + productId));
        product.setPrice(product.getPrice() + delta);
    }


    public List<Product> findByPriceBetween(Integer min, Integer max) {
        return productRepository.findAllByPriceBetween(min, max);
    }

    public List<Product> findByPriceMore(Integer min) {
        return productRepository.findAllByPriceMore(min);
    }

    public List<Product> findByPriceLess(Integer max) {
        return productRepository.findAllByPriceLess(max);
    }

    public ProductDto saveAndFlush(ProductDto productDto) {
        Product product = new Product(productDto.getTitle(), productDto.getPrice());
        product.setCreationDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").format(new Date()));
        return new ProductDto(productRepository.saveAndFlush(product));
    }
}

