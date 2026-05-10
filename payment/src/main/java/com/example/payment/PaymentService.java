package com.example.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;


    public List<Payment> getAllPayments() {
        return repository.findAll();
    }


    public Payment getPaymentById(Long id) {
        return repository.findById(id).orElse(null);
    }


    public Payment savePayment(Payment payment) {
        return repository.save(payment);
    }


    public Payment updatePayment(Long id, Payment updatedPayment) {
        return repository.findById(id).map(existingPayment -> {
            existingPayment.setStatus(updatedPayment.getStatus());
            existingPayment.setAmount(updatedPayment.getAmount());
            existingPayment.setOrderId(updatedPayment.getOrderId());
            return repository.save(existingPayment);
        }).orElse(null);
    }


    public boolean deletePayment(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}