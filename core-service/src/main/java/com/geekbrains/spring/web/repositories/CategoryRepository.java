package com.geekbrains.spring.web.repositories;

import com.geekbrains.spring.web.entities.Category;
import com.geekbrains.spring.web.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Product> {
}

