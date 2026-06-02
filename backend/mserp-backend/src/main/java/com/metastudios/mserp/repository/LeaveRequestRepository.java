package com.metastudios.mserp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metastudios.mserp.entity.LeaveRequest;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
}
