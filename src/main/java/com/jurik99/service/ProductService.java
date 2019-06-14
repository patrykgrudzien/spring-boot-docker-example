package com.jurik99.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.jurik99.model.Product;
import com.jurik99.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(final Long id) {
        return productRepository.findById(id);
    }

    public Product save(final Product stock) {
        return productRepository.save(stock);
    }

    public void deleteById(final Long id) {
        productRepository.deleteById(id);
    }
}
