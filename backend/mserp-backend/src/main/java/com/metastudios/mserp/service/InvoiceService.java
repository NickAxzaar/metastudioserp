package com.metastudios.mserp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metastudios.mserp.dto.InvoiceDTO;
import com.metastudios.mserp.dto.InvoiceRequestDTO;
import com.metastudios.mserp.entity.Customer;
import com.metastudios.mserp.entity.Invoice;
import com.metastudios.mserp.entity.SalesOrder;
import com.metastudios.mserp.exception.ResourceNotFoundException;
import com.metastudios.mserp.repository.CustomerRepository;
import com.metastudios.mserp.repository.InvoiceRepository;
import com.metastudios.mserp.repository.SalesOrderRepository;

@Service
@Transactional
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final SalesOrderRepository salesOrderRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, CustomerRepository customerRepository, 
            SalesOrderRepository salesOrderRepository) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
        this.salesOrderRepository = salesOrderRepository;
    }

    public List<InvoiceDTO> getAllInvoices() {
        return invoiceRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public InvoiceDTO getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));
        return convertToDTO(invoice);
    }

    public InvoiceDTO createInvoice(InvoiceRequestDTO request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + request.getCustomerId()));

        SalesOrder salesOrder = null;
        if (request.getSalesOrderId() != null) {
            salesOrder = salesOrderRepository.findById(request.getSalesOrderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sales order not found with id: " + request.getSalesOrderId()));
        }

        Invoice invoice = Invoice.builder()
                .customer(customer)
                .salesOrder(salesOrder)
                .invoiceNumber(request.getInvoiceNumber())
                .invoiceDate(request.getInvoiceDate())
                .dueDate(request.getDueDate())
                .totalAmount(request.getTotalAmount())
                .status(request.getStatus())
                .notes(request.getNotes())
                .build();

        Invoice savedInvoice = invoiceRepository.save(invoice);
        return convertToDTO(savedInvoice);
    }

    public void deleteInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));
        invoiceRepository.delete(invoice);
    }

    private InvoiceDTO convertToDTO(Invoice invoice) {
        InvoiceDTO dto = new InvoiceDTO();
        dto.setId(invoice.getId());
        dto.setCustomerId(invoice.getCustomer().getId());
        dto.setCustomerName(invoice.getCustomer().getCustomerName());
        dto.setCustomerEmail(invoice.getCustomer().getEmail());
        if (invoice.getSalesOrder() != null) {
            dto.setSalesOrderId(invoice.getSalesOrder().getId());
            dto.setSalesOrderCode(invoice.getSalesOrder().getOrderCode());
        }
        dto.setInvoiceNumber(invoice.getInvoiceNumber());
        dto.setInvoiceDate(invoice.getInvoiceDate());
        dto.setDueDate(invoice.getDueDate());
        dto.setTotalAmount(invoice.getTotalAmount());
        dto.setStatus(invoice.getStatus());
        dto.setNotes(invoice.getNotes());
        return dto;
    }
}
