package com.metastudios.mserp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metastudios.mserp.dto.PurchaseOrderDTO;
import com.metastudios.mserp.dto.PurchaseOrderRequestDTO;
import com.metastudios.mserp.entity.PurchaseOrder;
import com.metastudios.mserp.entity.Vendor;
import com.metastudios.mserp.exception.ResourceNotFoundException;
import com.metastudios.mserp.repository.PurchaseOrderRepository;
import com.metastudios.mserp.repository.VendorRepository;

@Service
@Transactional
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final VendorRepository vendorRepository;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, VendorRepository vendorRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.vendorRepository = vendorRepository;
    }

    public List<PurchaseOrderDTO> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PurchaseOrderDTO getPurchaseOrderById(Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase order not found with id: " + id));
        return convertToDTO(purchaseOrder);
    }

    public PurchaseOrderDTO createPurchaseOrder(PurchaseOrderRequestDTO request) {
        Vendor vendor = vendorRepository.findById(request.getVendorId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + request.getVendorId()));

        PurchaseOrder purchaseOrder = PurchaseOrder.builder()
                .vendor(vendor)
                .poCode(request.getPoCode())
                .orderDate(request.getOrderDate())
                .totalAmount(request.getTotalAmount())
                .status(request.getStatus())
                .notes(request.getNotes())
                .build();

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        return convertToDTO(savedPurchaseOrder);
    }

    public void deletePurchaseOrder(Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase order not found with id: " + id));
        purchaseOrderRepository.delete(purchaseOrder);
    }

    private PurchaseOrderDTO convertToDTO(PurchaseOrder purchaseOrder) {
        PurchaseOrderDTO dto = new PurchaseOrderDTO();
        dto.setId(purchaseOrder.getId());
        dto.setVendorId(purchaseOrder.getVendor().getId());
        dto.setVendorName(purchaseOrder.getVendor().getVendorName());
        dto.setVendorEmail(purchaseOrder.getVendor().getEmail());
        dto.setPoCode(purchaseOrder.getPoCode());
        dto.setOrderDate(purchaseOrder.getOrderDate());
        dto.setTotalAmount(purchaseOrder.getTotalAmount());
        dto.setStatus(purchaseOrder.getStatus());
        dto.setNotes(purchaseOrder.getNotes());
        return dto;
    }
}
