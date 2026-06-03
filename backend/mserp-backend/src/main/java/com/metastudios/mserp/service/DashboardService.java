package com.metastudios.mserp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metastudios.mserp.dto.DashboardDTO;
import com.metastudios.mserp.repository.AttendanceRepository;
import com.metastudios.mserp.repository.CustomerRepository;
import com.metastudios.mserp.repository.EmployeeRepository;
import com.metastudios.mserp.repository.LeadRepository;
import com.metastudios.mserp.repository.LeaveRequestRepository;
import com.metastudios.mserp.repository.ProductRepository;
import com.metastudios.mserp.repository.UserRepository;

@Service
@Transactional
public class DashboardService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final LeadRepository leadRepository;
    private final ProductRepository productRepository;
    private final AttendanceRepository attendanceRepository;
    private final LeaveRequestRepository leaveRequestRepository;

    public DashboardService(
            UserRepository userRepository,
            EmployeeRepository employeeRepository,
            CustomerRepository customerRepository,
            LeadRepository leadRepository,
            ProductRepository productRepository,
            AttendanceRepository attendanceRepository,
            LeaveRequestRepository leaveRequestRepository) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.leadRepository = leadRepository;
        this.productRepository = productRepository;
        this.attendanceRepository = attendanceRepository;
        this.leaveRequestRepository = leaveRequestRepository;
    }

    public DashboardDTO getDashboardData() {
        DashboardDTO dashboard = new DashboardDTO();
        dashboard.setTotalUsers(userRepository.count());
        dashboard.setTotalEmployees(employeeRepository.count());
        dashboard.setTotalCustomers(customerRepository.count());
        dashboard.setTotalLeads(leadRepository.count());
        dashboard.setTotalProducts(productRepository.count());
        dashboard.setTotalAttendanceRecords(attendanceRepository.count());
        dashboard.setTotalLeaveRequests(leaveRequestRepository.count());
        return dashboard;
    }
}
