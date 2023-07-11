package com.mobilestore.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mobilestore.demo.model.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
    
}
