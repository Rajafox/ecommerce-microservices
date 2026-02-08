package com.ecommerce.product.controller;

import com.ecommerce.product.domain.Category;
import com.ecommerce.product.dto.CreateCategoryRequest;
import com.ecommerce.product.dto.UpdateCategoryRequest;
import com.ecommerce.product.service.CategoryService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "Categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public Category create(@RequestBody CreateCategoryRequest request) {
        return service.create(request.getName());
    }

    // READ (PUBLIC)
    @GetMapping
    @PermitAll
    public List<Category> getAll() {
        return service.getAll();
    }

    //UPDATE
    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public Category update(
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Category ID", required = true)
            @PathVariable("id")  Long id,
            @RequestBody UpdateCategoryRequest request) {

        return service.update(id, request.getName());
    }

    //DELETE
    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public void delete(@Parameter(name = "id", in = ParameterIn.PATH, description = "Category ID", required = true)
                           @PathVariable("id")  Long id) {
        service.delete(id);
    }
}

