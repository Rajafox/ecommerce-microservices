package com.ecommerce.product.controller;

import com.ecommerce.product.domain.ProductImage;
import com.ecommerce.product.dto.AddImageRequest;
import com.ecommerce.product.service.ProductImageService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
    public ProductImage addImage(
            @Parameter(name = "productId", in = ParameterIn.PATH, description = "Product ID", required = true)
            @PathVariable("productId")  Long productId,
            @RequestBody AddImageRequest request) {

        return service.addImage(productId, request.getImageUrl());
    }

    @GetMapping
    public List<ProductImage> getImages(@Parameter(name = "productId", in = ParameterIn.PATH, description = "Product ID", required = true)
                                            @PathVariable("productId")  Long productId) {
        return service.getImages(productId);
    }

    @DeleteMapping("/{imageId}")
    public void deleteImage( @Parameter(name = "imageId", in = ParameterIn.PATH, description = "Image ID", required = true)
                                 @PathVariable("imageId")  Long imageId ) {
        service.deleteImage(imageId);
    }
}
