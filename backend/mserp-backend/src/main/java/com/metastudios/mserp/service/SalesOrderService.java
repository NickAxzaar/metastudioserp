package com.metastudios.mserp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metastudios.mserp.dto.SalesOrderDTO;
import com.metastudios.mserp.dto.SalesOrderRequestDTO;
import com.metastudios.mserp.entity.Customer;
import com.metastudios.mserp.entity.SalesOrder;
import com.metastudios.mserp.exception.ResourceNotFoundException;
import com.metastudios.mserp.repository.CustomerRepository;
import com.metastudios.mserp.repository.SalesOrderRepository;

@Service
@Transactional
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;
    private final CustomerRepository customerRepository;

    public SalesOrderService(SalesOrderRepository salesOrderRepository, CustomerRepository customerRepository) {
        this.salesOrderRepository = salesOrderRepository;
        this.customerRepository = customerRepository;
    }

    public List<SalesOrderDTO> getAllSalesOrders() {
        return salesOrderRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SalesOrderDTO getSalesOrderById(Long id) {
        SalesOrder salesOrder = salesOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sales order not found with id: " + id));
        return convertToDTO(salesOrder);
    }

    public SalesOrderDTO createSalesOrder(SalesOrderRequestDTO request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + request.getCustomerId()));

        SalesOrder salesOrder = SalesOrder.builder()
                .customer(customer)
                .orderCode(request.getOrderCode())
                .orderDate(request.getOrderDate())
                .totalAmount(request.getTotalAmount())
                .status(request.getStatus())
                .notes(request.getNotes())
                .build();

        SalesOrder savedSalesOrder = salesOrderRepository.save(salesOrder);
        return convertToDTO(savedSalesOrder);
    }

    public void deleteSalesOrder(Long id) {
        SalesOrder salesOrder = salesOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sales order not found with id: " + id));
        salesOrderRepository.delete(salesOrder);
    }

    @Transactional
    public SalesOrderDTO fulfillSalesOrder(Long id) {
        SalesOrder salesOrder = salesOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sales order not found with id: " + id));
        
        salesOrder.setStatus("FULFILLED");
        SalesOrder updatedSalesOrder = salesOrderRepository.save(salesOrder);
        
        // Dashboard and order counts are automatically updated through repository operations
        
        return convertToDTO(updatedSalesOrder);
    }

    private SalesOrderDTO convertToDTO(SalesOrder salesOrder) {
        SalesOrderDTO dto = new SalesOrderDTO();
        dto.setId(salesOrder.getId());
        dto.setCustomerId(salesOrder.getCustomer().getId());
        dto.setCustomerName(salesOrder.getCustomer().getCustomerName());
        dto.setCustomerEmail(salesOrder.getCustomer().getEmail());
        dto.setOrderCode(salesOrder.getOrderCode());
        dto.setOrderDate(salesOrder.getOrderDate());
        dto.setTotalAmount(salesOrder.getTotalAmount());
        dto.setStatus(salesOrder.getStatus());
        dto.setNotes(salesOrder.getNotes());
        return dto;
    }
}
