package com.metastudios.mserp.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PaymentRequestDTO {

    @NotNull(message = "Invoice ID is required")
    @Positive(message = "Invoice ID must be a positive number")
    private Long invoiceId;

    @NotNull(message = "Payment date is required")
    private LocalDate paymentDate;

    @NotNull(message = "Payment amount is required")
    @Positive(message = "Payment amount must be a positive number")
    private Double paymentAmount;

    @NotBlank(message = "Payment method is required")
    @Size(min = 1, max = 50, message = "Payment method must be between 1 and 50 characters")
    private String paymentMethod;

    @NotBlank(message = "Status is required")
    private String status;

    @Size(max = 100, message = "Reference number must not exceed 100 characters")
    private String reference;
}
