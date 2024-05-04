package id.ac.ui.cs.advprog.eshop.service;

import enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertNull;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderRepository orderRepository;

    List<Order> orders;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        orders = new ArrayList<>();
        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b", products,
                1708560000L, "Safira Sudrajat");
        orders.add(order1);
        Order order2 = new Order("7f9e15bb-4b15-42f4-aebc-c3af385fb078", products, 1708570000L, "Safira Sudrajat");
        orders.add(order2);
    }

    @Test
    void testCreateOrder() {
        Order order = orders.get(1);
        doReturn(order).when(orderRepository).save(order);

        Order result = orderService.createOrder(order);
        verify(orderRepository, times(1)).save(order);
        assertEquals(order.getId(), result.getId());
    }

    //Create unhappy path test: Use createOrder to add an already existing Order.
    @Test
    void testCreateOrderIfAlreadyExists() {
        Order order = orders.get(1);
        doReturn(order).when(orderRepository).findById(order.getId());

        Assertions.assertNull(orderService.createOrder(order));
        verify(orderRepository, times(0)).save(order);
    }

    // Create a happy path test: Use updateStatus to update status of an Order.
    @Test
    void testUpdateStatus() {
        Order order = orders.get(1);
        Order newOrder = new Order(order.getId(), order.getProducts(), order.getOrderTime(),
                order.getAuthor(), OrderStatus.SUCCESS.getValue());
        doReturn(order).when(orderRepository).findById(order.getId());
        doReturn(newOrder).when(orderRepository).save(any(Order.class));

        Order result = orderService.updateStatus(order.getId(), OrderStatus.SUCCESS.getValue());

        assertEquals(order.getId(), result.getId());
        assertEquals(OrderStatus.SUCCESS.getValue(), result.getStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    //Create unhappy path test: Use updateStatus to update status of an Order using invalid statusstring.
    @Test
    void testUpdateStatusInvalidStatus() {
        Order order = orders.get(1);
        doReturn(order).when(orderRepository).findById(order.getId());

        assertThrows(IllegalArgumentException.class,
                () -> orderService.updateStatus(order.getId(), "MEOW"));

        verify(orderRepository, times(0)).save(any(Order.class));
    }

    //Create an unhappy path test: Use updateStatus with an ID that is never used.

    @Test
    void testUpdateStatusInvalidOrderId() {
        doReturn(null).when(orderRepository).findById("zczc");

        assertThrows(NoSuchElementException.class,
                () -> orderService.updateStatus("zczc", OrderStatus.SUCCESS.getValue()));

        verify(orderRepository, times(

                0)).save(any(Order.class));

        // Create a happy path test: Use findById to find Order using a valid ID.
    }

    @Test
    void testFindByIdIfIdFound() {
        Order order = orders.get(1);
        doReturn(order).when(orderRepository).findById(order.getId());

        Order result = orderService.findById(order.getId());
        assertEquals(order.getId(), result.getId());
    }

    //Create an unhappy path test: Use findById to find Order using an ID that is never used.
    @Test
    void testFindByIdIfIdNotFound() {
        doReturn(null).when(orderRepository).findById("zczc");
        Assertions.assertNull(orderService.findById("zczc"));
    }

    // Create a happy path test: Use findAllByAuthor to find Order using a valid author name.
    @Test
    void testFindAllByAuthorIfAuthorCorrect() {
        Order order = orders.get(1);
        doReturn(orders).when(orderRepository).findAllByAuthor(order.getAuthor());

        List<Order> results = orderService.findAllByAuthor(order.getAuthor());
        for (Order result : results) {
            assertEquals(order.getAuthor(), result.getAuthor());
        }
            assertEquals(2, results.size());

        }

    //Create an unhappy path test: Use findAl1ByAuthor to find Order using an all-lowercasename.
    @Test
    void testFindAllByAuthorIfAllLowercase() {
        Order order = orders.get(1);
        doReturn(new ArrayList<Order>()).when(orderRepository)
                .findAllByAuthor(order.getAuthor().toLowerCase());

        List<Order> results = orderService.findAllByAuthor(
                order.getAuthor().toLowerCase());
        assertTrue(results.isEmpty());
    }

}