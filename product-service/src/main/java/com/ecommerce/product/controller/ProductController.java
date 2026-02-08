package com.ecommerce.product.controller;

import com.ecommerce.product.config.OpenApiConfig;
import com.ecommerce.product.service.ProductService;
import com.ecommerce.product.domain.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Product> getAll() {
        return service.findAll();
    }

    @Operation(summary = "Get product by id")
    @GetMapping("/{id}")
    public Product getById(@Parameter(name = "id", in = ParameterIn.PATH, description = "Product ID", required = true)
                               @PathVariable("id")  Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Create product (ADMIN only)")
    @SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME)
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Product create(@RequestBody Product product) {
        return service.create(product);
    }

    @GetMapping("/search")
    public Page<Product> searchProducts(
            @RequestParam(required = false, name= "name") String name,
            @RequestParam(required = false, name= "brand") String brand,
            @RequestParam(required = false, name= "categoryId") Long categoryId,
            @RequestParam(required = false, name= "minPrice") BigDecimal minPrice,
            @RequestParam(required = false, name= "maxPrice") BigDecimal maxPrice,
            @RequestParam(required = false, name= "inStock") Boolean inStock,
            @RequestParam(required = false, defaultValue = "0", name= "page") int page,
            @RequestParam(required = false, defaultValue = "20", name= "size") int size,
            @RequestParam(required = false, defaultValue = "name", name= "sortBy") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC", name= "sortDir") String sortDir) {

        // Validate sort field to prevent invalid property errors
        String validatedSortBy = validateSortField(sortBy);
        Sort.Direction direction = "DESC".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, validatedSortBy));

        return service.search(
                name, brand, categoryId,
                minPrice, maxPrice, inStock, pageable
        );
    }

    private String validateSortField(String sortBy) {
        // List of valid sort fields based on Product entity
        List<String> validFields = List.of("name", "price", "stock", "brand", "id");

        if (sortBy == null || !validFields.contains(sortBy.toLowerCase())) {
            return "name"; // default sort field
        }
        return sortBy;
    }

}




