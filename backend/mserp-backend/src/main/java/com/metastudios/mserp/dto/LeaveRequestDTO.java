package com.metastudios.mserp.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class LeaveRequestDTO {
    private Long id;
    private Long employeeId;
    private String employeeCode;
    private String employeeName;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String status;
}
