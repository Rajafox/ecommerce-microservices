package com.ecommerce.product.controller;

import com.ecommerce.product.config.OpenApiConfig;
import com.ecommerce.product.service.ProductService;
import com.ecommerce.product.domain.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Products")
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @Operation(summary = "Get all products")
    @GetMapping
    public List<Product> getAll() {
        return service.findAll();
    }

    @Operation(summary = "Get product by id")
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Create product (ADMIN only)")
    @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Product create(@RequestBody Product product) {
        return service.create(product);
    }
}



