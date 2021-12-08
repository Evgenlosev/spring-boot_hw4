package com.geekbrains.spring.web.repositories;

import com.geekbrains.spring.web.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {


    List<Product> findAllByPriceBetween(Integer min, Integer max);

    @Query("select p from Product p where p.price > :min")
    List<Product> findAllByPriceMore(Integer min);

    @Query("select p from Product p where p.price < :max")
    List<Product> findAllByPriceLess(Integer max);
}

