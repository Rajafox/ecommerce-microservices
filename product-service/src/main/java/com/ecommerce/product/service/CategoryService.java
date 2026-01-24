package com.ecommerce.product.service;

import com.ecommerce.product.domain.Category;
import com.ecommerce.product.repository.CategoryRepository;
import com.ecommerce.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryService(CategoryRepository categoryRepository,
                           ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public Category create(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new RuntimeException("Category already exists");
        }

        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category update(Long id, String newName) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (categoryRepository.existsByName(newName)) {
            throw new RuntimeException("Category name already exists");
        }

        category.setName(newName);
        return categoryRepository.save(category);
    }

    public void delete(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // 🔒 Safety check: category in use
        boolean inUse =
                productRepository.existsByCategoryId(id);

        if (inUse) {
            throw new RuntimeException(
                    "Cannot delete category that is assigned to products");
        }

        categoryRepository.delete(category);
    }
}

