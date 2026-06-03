package com.metastudios.mserp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metastudios.mserp.entity.PurchaseOrder;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    Optional<PurchaseOrder> findByPoCode(String poCode);

    List<PurchaseOrder> findByVendorId(Long vendorId);

    List<PurchaseOrder> findByStatus(String status);
}
