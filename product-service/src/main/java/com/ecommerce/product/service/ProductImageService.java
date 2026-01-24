package com.ecommerce.product.service;

import com.ecommerce.product.domain.Product;
import com.ecommerce.product.domain.ProductImage;
import com.ecommerce.product.repository.ProductImageRepository;
import com.ecommerce.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService {

    private final ProductRepository productRepo;
    private final ProductImageRepository imageRepo;

    public ProductImageService(ProductRepository productRepo,
                               ProductImageRepository imageRepo) {
        this.productRepo = productRepo;
        this.imageRepo = imageRepo;
    }

    public ProductImage addImage(Long productId, String imageUrl) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductImage image = new ProductImage();
        image.setImageUrl(imageUrl);
        image.setProduct(product);

        return imageRepo.save(image);
    }

    public void deleteImage(Long imageId) {
        imageRepo.deleteById(imageId);
    }

    public List<ProductImage> getImages(Long productId) {
        return imageRepo.findByProductId(productId);
    }
}
