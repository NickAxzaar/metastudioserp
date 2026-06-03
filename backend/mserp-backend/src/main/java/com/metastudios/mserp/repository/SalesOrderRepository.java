package com.metastudios.mserp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metastudios.mserp.entity.SalesOrder;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    Optional<SalesOrder> findByOrderCode(String orderCode);

    List<SalesOrder> findByCustomerId(Long customerId);

    List<SalesOrder> findByStatus(String status);
}
