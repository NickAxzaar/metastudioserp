package com.metastudios.mserp.dto;

import lombok.Data;

@Data
public class DashboardDTO {
    private Long totalUsers;
    private Long totalEmployees;
    private Long totalCustomers;
    private Long totalLeads;
    private Long totalProducts;
    private Long totalAttendanceRecords;
    private Long totalLeaveRequests;
}
