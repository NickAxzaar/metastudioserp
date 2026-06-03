package com.metastudios.mserp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metastudios.mserp.dto.LeadDTO;
import com.metastudios.mserp.dto.LeadRequestDTO;
import com.metastudios.mserp.entity.Lead;
import com.metastudios.mserp.exception.ResourceNotFoundException;
import com.metastudios.mserp.repository.LeadRepository;

@Service
@Transactional
public class LeadService {

    private final LeadRepository leadRepository;

    public LeadService(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    public List<LeadDTO> getAllLeads() {
        return leadRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public LeadDTO getLeadById(Long id) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lead not found with id: " + id));
        return convertToDTO(lead);
    }

    public LeadDTO createLead(LeadRequestDTO request) {
        Lead lead = Lead.builder()
                .leadName(request.getLeadName())
                .companyName(request.getCompanyName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .source(request.getSource())
                .status(request.getStatus())
                .assignedTo(request.getAssignedTo())
                .createdDate(LocalDate.now())
                .build();

        Lead savedLead = leadRepository.save(lead);
        return convertToDTO(savedLead);
    }

    public void deleteLead(Long id) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lead not found with id: " + id));
        leadRepository.delete(lead);
    }

    private LeadDTO convertToDTO(Lead lead) {
        LeadDTO dto = new LeadDTO();
        dto.setId(lead.getId());
        dto.setLeadName(lead.getLeadName());
        dto.setCompanyName(lead.getCompanyName());
        dto.setEmail(lead.getEmail());
        dto.setPhoneNumber(lead.getPhoneNumber());
        dto.setSource(lead.getSource());
        dto.setStatus(lead.getStatus());
        dto.setAssignedTo(lead.getAssignedTo());
        dto.setCreatedDate(lead.getCreatedDate());
        return dto;
    }
}
