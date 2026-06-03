package com.metastudios.mserp.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ExpenseDTO {
    private Long id;
    private String expenseCode;
    private String category;
    private Double amount;
    private LocalDate expenseDate;
    private String description;
    private String status;
}
