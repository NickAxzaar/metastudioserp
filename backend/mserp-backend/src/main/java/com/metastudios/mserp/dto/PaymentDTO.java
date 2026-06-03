package com.metastudios.mserp.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class PaymentDTO {
    private Long id;
    private Long invoiceId;
    private String invoiceNumber;
    private String customerName;
    private LocalDate paymentDate;
    private Double paymentAmount;
    private String paymentMethod;
    private String status;
    private String reference;
}
