package com.ecommerce.product.service;

import com.ecommerce.product.domain.Product;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.repository.spec.ProductSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product create(Product product) {
        return repository.save(product);
    }

    public Page<Product> search(
            String name,
            String brand,
            Long categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Boolean inStock,
            Pageable pageable) {

        Specification<Product> spec =
                Specification.where(ProductSpecifications.nameContains(name))
                        .and(ProductSpecifications.hasBrand(brand))
                        .and(ProductSpecifications.hasCategory(categoryId))
                        .and(ProductSpecifications.priceBetween(minPrice, maxPrice))
                        .and(ProductSpecifications.inStock(inStock));

        return repository.findAll(spec, pageable);
    }
}

