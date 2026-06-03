package com.metastudios.mserp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metastudios.mserp.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByProductId(Long productId);
}
