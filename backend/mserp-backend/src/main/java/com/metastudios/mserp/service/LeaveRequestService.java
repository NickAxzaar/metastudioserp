package com.metastudios.mserp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metastudios.mserp.dto.LeaveRequestDTO;
import com.metastudios.mserp.dto.LeaveRequestRequestDTO;
import com.metastudios.mserp.entity.Employee;
import com.metastudios.mserp.entity.LeaveRequest;
import com.metastudios.mserp.exception.ResourceNotFoundException;
import com.metastudios.mserp.repository.EmployeeRepository;
import com.metastudios.mserp.repository.LeaveRequestRepository;

@Service
@Transactional
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;

    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository, EmployeeRepository employeeRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<LeaveRequestDTO> getAllLeaveRequests() {
        return leaveRequestRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public LeaveRequestDTO getLeaveRequestById(Long id) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found with id: " + id));
        return convertToDTO(leaveRequest);
    }

    public LeaveRequestDTO createLeaveRequest(LeaveRequestRequestDTO request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + request.getEmployeeId()));

        LeaveRequest leaveRequest = LeaveRequest.builder()
                .employee(employee)
                .leaveType(request.getLeaveType())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .reason(request.getReason())
                .status(request.getStatus())
                .build();

        LeaveRequest savedLeaveRequest = leaveRequestRepository.save(leaveRequest);
        return convertToDTO(savedLeaveRequest);
    }

    public void deleteLeaveRequest(Long id) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found with id: " + id));
        leaveRequestRepository.delete(leaveRequest);
    }

    private LeaveRequestDTO convertToDTO(LeaveRequest leaveRequest) {
        LeaveRequestDTO dto = new LeaveRequestDTO();
        dto.setId(leaveRequest.getId());
        dto.setEmployeeId(leaveRequest.getEmployee().getId());
        dto.setEmployeeCode(leaveRequest.getEmployee().getEmployeeCode());
        dto.setEmployeeName(leaveRequest.getEmployee().getUser().getName());
        dto.setLeaveType(leaveRequest.getLeaveType());
        dto.setStartDate(leaveRequest.getStartDate());
        dto.setEndDate(leaveRequest.getEndDate());
        dto.setReason(leaveRequest.getReason());
        dto.setStatus(leaveRequest.getStatus());
        return dto;
    }
}
