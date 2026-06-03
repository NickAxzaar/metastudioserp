package com.metastudios.mserp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductRequestDTO {

    @NotBlank(message = "Product name is required")
    @Size(min = 1, max = 100, message = "Product name must be between 1 and 100 characters")
    private String productName;

    @NotBlank(message = "Product code is required")
    @Size(min = 1, max = 50, message = "Product code must be between 1 and 50 characters")
    private String productCode;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive number")
    private Double price;

    @NotNull(message = "Quantity is required")
    @PositiveOrZero(message = "Quantity must be zero or positive")
    private Integer quantity;

    @NotBlank(message = "Category is required")
    @Size(min = 1, max = 50, message = "Category must be between 1 and 50 characters")
    private String category;
}
