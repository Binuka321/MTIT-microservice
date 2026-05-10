package com.example.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Order getOrderById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Order saveOrder(Order order) {
        return repository.save(order);
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        return repository.findById(id).map(o -> {
            o.setStatus(updatedOrder.getStatus());
            o.setTotalAmount(updatedOrder.getTotalAmount());
            o.setProductIds(updatedOrder.getProductIds());
            o.setUserId(updatedOrder.getUserId());
            return repository.save(o);
        }).orElse(null);
    }

    public boolean deleteOrder(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}