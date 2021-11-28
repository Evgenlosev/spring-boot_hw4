package com.geekbrains.spring.web.services;

import com.geekbrains.spring.web.data.Product;
import com.geekbrains.spring.web.repositories.ProductDao;
import com.geekbrains.spring.web.repositories.ProductDaoImpl;
import com.geekbrains.spring.web.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductDaoImpl productDao;

    public ProductService(ProductDaoImpl productDao) {
        this.productDao = productDao;
    }

    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    public void deleteById(Long id) {
        productDao.deleteById(id);
    }

    public void changePrice(Long productId, Integer delta) {
        productDao.changePrice(productId, delta);
    }

//    public void changeCost(Long productId, Integer delta) {
//        Product product = productRepository.findById(productId);
//        product.setCost(product.getCost() + delta);
//    }
}
