package com.ecommerce.product.controller;

import com.ecommerce.product.config.OpenApiConfig;
import com.ecommerce.product.service.ProductService;
import com.ecommerce.product.domain.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
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

    @GetMapping("/search")
    public Page<Product> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Boolean inStock,
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {

        return service.search(
                name, brand, categoryId,
                minPrice, maxPrice, inStock, pageable
        );
    }

}



