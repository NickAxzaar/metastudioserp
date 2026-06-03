package com.metastudios.mserp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metastudios.mserp.dto.ProductDTO;
import com.metastudios.mserp.dto.ProductRequestDTO;
import com.metastudios.mserp.entity.Product;
import com.metastudios.mserp.exception.ResourceNotFoundException;
import com.metastudios.mserp.repository.ProductRepository;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return convertToDTO(product);
    }

    public ProductDTO createProduct(ProductRequestDTO request) {
        Product product = Product.builder()
                .productName(request.getProductName())
                .productCode(request.getProductCode())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .category(request.getCategory())
                .isActive(true)
                .build();

        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setProductName(product.getProductName());
        dto.setProductCode(product.getProductCode());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setCategory(product.getCategory());
        dto.setIsActive(product.getIsActive());
        return dto;
    }
}
