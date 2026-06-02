package com.metastudios.mserp.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

@Data
public class AttendanceDTO {
    private Long id;
    private Long employeeId;
    private String employeeCode;
    private String employeeName;
    private LocalDate attendanceDate;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private String status;
}
