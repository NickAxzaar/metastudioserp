package com.metastudios.mserp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metastudios.mserp.dto.DashboardDTO;
import com.metastudios.mserp.repository.AttendanceRepository;
import com.metastudios.mserp.repository.CustomerRepository;
import com.metastudios.mserp.repository.EmployeeRepository;
import com.metastudios.mserp.repository.ExpenseRepository;
import com.metastudios.mserp.repository.InvoiceRepository;
import com.metastudios.mserp.repository.LeadRepository;
import com.metastudios.mserp.repository.LeaveRequestRepository;
import com.metastudios.mserp.repository.PaymentRepository;
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
    private final InvoiceRepository invoiceRepository;
    private final ExpenseRepository expenseRepository;
    private final PaymentRepository paymentRepository;

    public DashboardService(
            UserRepository userRepository,
            EmployeeRepository employeeRepository,
            CustomerRepository customerRepository,
            LeadRepository leadRepository,
            ProductRepository productRepository,
            AttendanceRepository attendanceRepository,
            LeaveRequestRepository leaveRequestRepository,
            InvoiceRepository invoiceRepository,
            ExpenseRepository expenseRepository,
            PaymentRepository paymentRepository) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.leadRepository = leadRepository;
        this.productRepository = productRepository;
        this.attendanceRepository = attendanceRepository;
        this.leaveRequestRepository = leaveRequestRepository;
        this.invoiceRepository = invoiceRepository;
        this.expenseRepository = expenseRepository;
        this.paymentRepository = paymentRepository;
    }

    public DashboardDTO getDashboardData() {
        DashboardDTO dashboard = new DashboardDTO();
        
        // Basic counts
        dashboard.setTotalUsers(userRepository.count());
        dashboard.setTotalEmployees(employeeRepository.count());
        dashboard.setTotalCustomers(customerRepository.count());
        dashboard.setTotalLeads(leadRepository.count());
        dashboard.setTotalProducts(productRepository.count());
        dashboard.setTotalAttendanceRecords(attendanceRepository.count());
        dashboard.setTotalLeaveRequests(leaveRequestRepository.count());
        
        // Financial metrics
        dashboard.setTotalRevenue(calculateTotalRevenue());
        dashboard.setTotalExpenses(calculateTotalExpenses());
        dashboard.setTotalPendingInvoices(countPendingInvoices());
        dashboard.setTotalPendingPayments(countPendingPayments());
        
        return dashboard;
    }

    private Double calculateTotalRevenue() {
        return invoiceRepository.findAll()
                .stream()
                .filter(invoice -> "PAID".equalsIgnoreCase(invoice.getStatus()) || "COMPLETED".equalsIgnoreCase(invoice.getStatus()))
                .mapToDouble(invoice -> invoice.getTotalAmount() != null ? invoice.getTotalAmount() : 0.0)
                .sum();
    }

    private Double calculateTotalExpenses() {
        return expenseRepository.findAll()
                .stream()
                .mapToDouble(expense -> expense.getAmount() != null ? expense.getAmount() : 0.0)
                .sum();
    }

    private Long countPendingInvoices() {
        return invoiceRepository.findAll()
                .stream()
                .filter(invoice -> "PENDING".equalsIgnoreCase(invoice.getStatus()))
                .count();
    }

    private Long countPendingPayments() {
        return paymentRepository.findAll()
                .stream()
                .filter(payment -> "PENDING".equalsIgnoreCase(payment.getStatus()))
                .count();
    }
}
