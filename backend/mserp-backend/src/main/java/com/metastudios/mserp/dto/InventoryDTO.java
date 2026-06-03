package com.metastudios.mserp.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class InventoryDTO {
    private Long id;
    private Long productId;
    private String productCode;
    private String productName;
    private Integer currentStock;
    private Integer minimumStock;
    private Integer maximumStock;
    private String warehouseLocation;
    private LocalDate lastUpdated;
}
