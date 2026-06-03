package com.metastudios.mserp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metastudios.mserp.dto.VendorDTO;
import com.metastudios.mserp.dto.VendorRequestDTO;
import com.metastudios.mserp.entity.Vendor;
import com.metastudios.mserp.exception.ResourceNotFoundException;
import com.metastudios.mserp.repository.VendorRepository;

@Service
@Transactional
public class VendorService {

    private final VendorRepository vendorRepository;

    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public VendorDTO getVendorById(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + id));
        return convertToDTO(vendor);
    }

    public VendorDTO createVendor(VendorRequestDTO request) {
        Vendor vendor = Vendor.builder()
                .vendorName(request.getVendorName())
                .vendorCode(request.getVendorCode())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .paymentTerms(request.getPaymentTerms())
                .isActive(true)
                .build();

        Vendor savedVendor = vendorRepository.save(vendor);
        return convertToDTO(savedVendor);
    }

    public void deleteVendor(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + id));
        vendorRepository.delete(vendor);
    }

    private VendorDTO convertToDTO(Vendor vendor) {
        VendorDTO dto = new VendorDTO();
        dto.setId(vendor.getId());
        dto.setVendorName(vendor.getVendorName());
        dto.setVendorCode(vendor.getVendorCode());
        dto.setEmail(vendor.getEmail());
        dto.setPhoneNumber(vendor.getPhoneNumber());
        dto.setAddress(vendor.getAddress());
        dto.setCity(vendor.getCity());
        dto.setState(vendor.getState());
        dto.setCountry(vendor.getCountry());
        dto.setPaymentTerms(vendor.getPaymentTerms());
        dto.setIsActive(vendor.getIsActive());
        return dto;
    }
}
