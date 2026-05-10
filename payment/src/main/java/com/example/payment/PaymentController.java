package com.example.payment;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/payments")
@Tag(name = "Payment Management", description = "APIs for Payment Service")
@OpenAPIDefinition(
        info = @Info(title = "Payment API", version = "1.0"),
        servers = {

                @Server(url = "http://localhost:8080/api", description = "Gateway Access (8080)"),

                @Server(url = "http://localhost:8083", description = "Direct Access (8083)")
        }
)
public class PaymentController {

    @Autowired
    private PaymentService service;

    @GetMapping
    @Operation(summary = "Get all payments")
    public List<Payment> getPayments() {
        return service.getAllPayments();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get payment by ID")
    public Payment getPaymentById(@PathVariable Long id) {
        Payment payment = service.getPaymentById(id);
        if (payment == null) {
            throw new RuntimeException("Payment not found with id: " + id);
        }
        return payment;
    }

    @PostMapping
    @Operation(summary = "Create a new payment")
    public Payment addPayment(@RequestBody Payment payment) {
        return service.savePayment(payment);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing payment")
    public Payment updatePayment(@PathVariable Long id, @RequestBody Payment payment) {
        Payment updated = service.updatePayment(id, payment);
        if (updated == null) {
            throw new RuntimeException("Payment not found with id: " + id);
        }
        return updated;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a payment record")
    public String deletePayment(@PathVariable Long id) {
        boolean deleted = service.deletePayment(id);
        return deleted ? "Payment deleted successfully" : "Payment not found";
    }
}