package com.example.simple_ecommerce.repository;

import com.example.simple_ecommerce.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByName(String name);
}
