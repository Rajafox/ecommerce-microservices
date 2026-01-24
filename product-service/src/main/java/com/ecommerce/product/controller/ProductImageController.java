package com.ecommerce.product.controller;

import com.ecommerce.product.domain.ProductImage;
import com.ecommerce.product.dto.AddImageRequest;
import com.ecommerce.product.service.ProductImageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products/{productId}/images")
public class ProductImageController {

    private final ProductImageService service;

    public ProductImageController(ProductImageService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ProductImage addImage(
            @PathVariable Long productId,
            @RequestBody AddImageRequest request) {

        return service.addImage(productId, request.getImageUrl());
    }

    @GetMapping
    public List<ProductImage> getImages(@PathVariable Long productId) {
        return service.getImages(productId);
    }

    @DeleteMapping("/{imageId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteImage(@PathVariable Long imageId) {
        service.deleteImage(imageId);
    }
}
