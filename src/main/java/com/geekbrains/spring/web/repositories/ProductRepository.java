package com.geekbrains.spring.web.repositories;

import com.geekbrains.spring.web.data.Product;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ProductRepository {
    private List<Product> products;

//    @PostConstruct
//    public void init() {
//        products = new ArrayList<>(List.of(
//                new Product(1L, "Bread", 40L),
//                new Product(2L, "Apples", 150L),
//                new Product(3L, "Milk", 60L),
//                new Product(4L, "Chocolate", 70L),
//                new Product(5L, "Cheese", 145L)
//        ));
//    }



    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(products);
    }

    public void deleteById(Long id) {
        products.removeIf(p -> p.getId().equals(id));
    }

    public Product findById(Long id) {
        return products.stream().filter(p -> p.getId().equals(id)).findFirst().get();
    }
}
