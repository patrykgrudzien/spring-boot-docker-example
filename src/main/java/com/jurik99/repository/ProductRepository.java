package com.jurik99.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jurik99.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {}
