package com.metastudios.mserp.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class InvoiceDTO {
    private Long id;
    private Long customerId;
    private String customerName;
    private String customerEmail;
    private Long salesOrderId;
    private String salesOrderCode;
    private String invoiceNumber;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private Double totalAmount;
    private String status;
    private String notes;
}
