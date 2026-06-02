package com.metastudios.mserp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import com.metastudios.mserp.dto.LeaveRequestDTO;
import com.metastudios.mserp.dto.LeaveRequestRequestDTO;
import com.metastudios.mserp.service.LeaveRequestService;

@RestController
@RequestMapping("/api/leave-requests")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    @GetMapping
    public ResponseEntity<List<LeaveRequestDTO>> getAllLeaveRequests() {
        return ResponseEntity.ok(leaveRequestService.getAllLeaveRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveRequestDTO> getLeaveRequestById(@PathVariable Long id) {
        return ResponseEntity.ok(leaveRequestService.getLeaveRequestById(id));
    }

    @PostMapping
    public ResponseEntity<LeaveRequestDTO> createLeaveRequest(
            @Valid @RequestBody LeaveRequestRequestDTO leaveRequestRequestDTO) {

        return new ResponseEntity<>(
                leaveRequestService.createLeaveRequest(leaveRequestRequestDTO),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeaveRequest(@PathVariable Long id) {

        leaveRequestService.deleteLeaveRequest(id);

        return ResponseEntity.noContent().build();
    }
}
