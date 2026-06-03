package com.metastudios.mserp.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String productName;
    private String productCode;
    private String description;
    private Double price;
    private Integer quantity;
    private String category;
    private Boolean isActive;
}
