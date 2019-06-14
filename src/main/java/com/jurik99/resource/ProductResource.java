package com.jurik99.resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jurik99.model.Product;
import com.jurik99.service.ProductService;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@Slf4j
@RequiredArgsConstructor
public class ProductResource {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody final Product product) {
        return ResponseEntity.ok(productService.save(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable final Long id) {
        return productService.findById(id)
                             .map(ResponseEntity::ok)
                             .orElseGet(() -> {
                                 log.error("Id " + id + " is not existed");
                                 return ResponseEntity.badRequest().build();
                             });
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable final Long id, @Valid @RequestBody final Product product) {
        return productService.findById(id)
                             .map(p -> ResponseEntity.ok(productService.save(product)))
                             .orElseGet(() -> {
                                 log.error("Id " + id + " is not existed");
                                 return ResponseEntity.badRequest().build();
                             });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable final Long id) {
        return productService.findById(id)
                             .map(p -> {
                                 productService.deleteById(id);
                                 return ResponseEntity.ok().build();
                             }).orElseGet(() -> {
                                 log.error("Id " + id + " is not existed");
                                 return ResponseEntity.badRequest().build();
                             });
    }
}
