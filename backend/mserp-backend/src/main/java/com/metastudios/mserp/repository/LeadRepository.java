package com.metastudios.mserp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metastudios.mserp.entity.Lead;

public interface LeadRepository extends JpaRepository<Lead, Long> {
    List<Lead> findByStatus(String status);

    Optional<Lead> findByEmail(String email);
}
