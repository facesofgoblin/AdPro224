package id.ac.ui.cs.advprog.eshop.service;//Create OrderService interface in src/main/java/service for the skeleton to avoid compileerrors.

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;

import java.util.List;

public interface OrderService {

    public Order createOrder(Order order);

    public Order updateStatus(String orderId, String status);

    public Order findById(String orderId);

    public List<Order> findAllByAuthor(String author);

}