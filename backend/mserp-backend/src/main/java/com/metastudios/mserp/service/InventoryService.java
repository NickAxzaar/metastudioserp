package com.metastudios.mserp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metastudios.mserp.dto.InventoryDTO;
import com.metastudios.mserp.dto.InventoryRequestDTO;
import com.metastudios.mserp.entity.Inventory;
import com.metastudios.mserp.entity.Product;
import com.metastudios.mserp.exception.ResourceNotFoundException;
import com.metastudios.mserp.repository.InventoryRepository;
import com.metastudios.mserp.repository.ProductRepository;

@Service
@Transactional
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public InventoryService(InventoryRepository inventoryRepository, ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    public List<InventoryDTO> getAllInventory() {
        return inventoryRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public InventoryDTO getInventoryById(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id: " + id));
        return convertToDTO(inventory);
    }

    public InventoryDTO createInventory(InventoryRequestDTO request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + request.getProductId()));

        Inventory inventory = Inventory.builder()
                .product(product)
                .currentStock(request.getCurrentStock())
                .minimumStock(request.getMinimumStock())
                .maximumStock(request.getMaximumStock())
                .warehouseLocation(request.getWarehouseLocation())
                .lastUpdated(LocalDate.now())
                .build();

        Inventory savedInventory = inventoryRepository.save(inventory);
        return convertToDTO(savedInventory);
    }

    public void deleteInventory(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id: " + id));
        inventoryRepository.delete(inventory);
    }

    @Transactional
    public void increaseStock(Long productId, Integer quantityAdded) {
        List<Inventory> inventoryList = inventoryRepository.findByProductId(productId);
        
        if (!inventoryList.isEmpty()) {
            Inventory inventory = inventoryList.get(0);
            inventory.setCurrentStock(inventory.getCurrentStock() + quantityAdded);
            inventory.setLastUpdated(LocalDate.now());
            inventoryRepository.save(inventory);
        }
    }

    @Transactional
    public void decreaseStock(Long productId, Integer quantityRemoved) {
        List<Inventory> inventoryList = inventoryRepository.findByProductId(productId);
        
        if (!inventoryList.isEmpty()) {
            Inventory inventory = inventoryList.get(0);
            if (inventory.getCurrentStock() >= quantityRemoved) {
                inventory.setCurrentStock(inventory.getCurrentStock() - quantityRemoved);
                inventory.setLastUpdated(LocalDate.now());
                inventoryRepository.save(inventory);
            } else {
                throw new ResourceNotFoundException("Insufficient stock for product id: " + productId);
            }
        }
    }

    private InventoryDTO convertToDTO(Inventory inventory) {
        InventoryDTO dto = new InventoryDTO();
        dto.setId(inventory.getId());
        dto.setProductId(inventory.getProduct().getId());
        dto.setProductCode(inventory.getProduct().getProductCode());
        dto.setProductName(inventory.getProduct().getProductName());
        dto.setCurrentStock(inventory.getCurrentStock());
        dto.setMinimumStock(inventory.getMinimumStock());
        dto.setMaximumStock(inventory.getMaximumStock());
        dto.setWarehouseLocation(inventory.getWarehouseLocation());
        dto.setLastUpdated(inventory.getLastUpdated());
        return dto;
    }
}
