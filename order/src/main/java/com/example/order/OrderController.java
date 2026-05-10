package com.example.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order Management", description = "APIs for Order Service")
@OpenAPIDefinition(
        info = @Info(title = "Order API", version = "1.0"),
        servers = {

                @Server(url = "http://localhost:8080/api", description = "Gateway Access"),
                @Server(url = "http://localhost:8082", description = "Direct Access")
        }
)
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all orders")
    public List<Order> getOrders() {
        return service.getAllOrders();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID")
    public Order getOrderById(@PathVariable Long id) {
        Order order = service.getOrderById(id);
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
        return order;
    }

    @PostMapping
    @Operation(summary = "Place a new order")
    public Order addOrder(@RequestBody Order order) {
        return service.saveOrder(order);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an order")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        Order updated = service.updateOrder(id, order);
        if (updated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
        return updated;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel/Delete an order")
    public String deleteOrder(@PathVariable Long id) {
        boolean deleted = service.deleteOrder(id);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
        return "Order deleted successfully";
    }
}