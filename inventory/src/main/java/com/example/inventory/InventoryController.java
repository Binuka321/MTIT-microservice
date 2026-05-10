package com.example.inventory;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/inventory") // Matches the stripped path from Gateway
@Tag(name = "Inventory Management", description = "APIs for Inventory Service")
@OpenAPIDefinition(
        info = @Info(title = "Inventory API", version = "1.0"),
        servers = {
                // Pointing to the Gateway URL with the required prefix
                @Server(url = "http://localhost:8080/api", description = "Gateway Access"),
                @Server(url = "http://localhost:8086", description = "Direct Access")
        }
)
public class InventoryController {

    @Autowired
    private InventoryService service;

    @GetMapping
    @Operation(summary = "Get all inventory items")
    public List<Inventory> getInventory() {
        return service.getAllInventory();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a single inventory item by ID")
    public Inventory getById(@PathVariable Long id) {
        return service.getInventoryById(id);
    }

    @PostMapping
    @Operation(summary = "Add a new inventory item")
    public Inventory addInventory(@RequestBody Inventory inventory) {
        return service.saveInventory(inventory);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing inventory item")
    public Inventory update(@PathVariable Long id, @RequestBody Inventory inventory) {
        return service.updateInventory(id, inventory);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an inventory item")
    public String delete(@PathVariable Long id) {
        boolean deleted = service.deleteInventory(id);
        return deleted ? "Deleted Successfully" : "Item Not Found";
    }
}