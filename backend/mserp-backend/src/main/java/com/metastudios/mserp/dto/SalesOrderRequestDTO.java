package com.metastudios.mserp.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SalesOrderRequestDTO {

    @NotNull(message = "Customer ID is required")
    @Positive(message = "Customer ID must be a positive number")
    private Long customerId;

    @NotBlank(message = "Order code is required")
    @Size(min = 1, max = 50, message = "Order code must be between 1 and 50 characters")
    private String orderCode;

    @NotNull(message = "Order date is required")
    private LocalDate orderDate;

    @NotNull(message = "Total amount is required")
    @Positive(message = "Total amount must be a positive number")
    private Double totalAmount;

    @NotBlank(message = "Status is required")
    private String status;

    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;
}
