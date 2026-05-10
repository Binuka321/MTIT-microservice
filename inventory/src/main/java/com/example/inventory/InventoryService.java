package com.example.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository repository;

    public List<Inventory> getAllInventory() {
        return repository.findAll();
    }

    public Inventory getInventoryById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Inventory saveInventory(Inventory inventory) {
        return repository.save(inventory);
    }

    public Inventory updateInventory(Long id, Inventory updatedInventory) {
        return repository.findById(id).map(item -> {
            item.setProductId(updatedInventory.getProductId());
            item.setQuantity(updatedInventory.getQuantity());
            item.setLocation(updatedInventory.getLocation());
            return repository.save(item);
        }).orElse(null);
    }

    public boolean deleteInventory(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}