package com.metastudios.mserp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class InventoryRequestDTO {

    @NotNull(message = "Product ID is required")
    @Positive(message = "Product ID must be a positive number")
    private Long productId;

    @NotNull(message = "Current stock is required")
    @Positive(message = "Current stock must be a positive number")
    private Integer currentStock;

    @NotNull(message = "Minimum stock is required")
    @Positive(message = "Minimum stock must be a positive number")
    private Integer minimumStock;

    @NotNull(message = "Maximum stock is required")
    @Positive(message = "Maximum stock must be a positive number")
    private Integer maximumStock;

    @Size(max = 100, message = "Warehouse location must not exceed 100 characters")
    private String warehouseLocation;
}
