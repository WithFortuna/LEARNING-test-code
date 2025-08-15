package sample.cafekiosk.spring.order.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sample.cafekiosk.spring.order.application.service.OrderService;
import sample.cafekiosk.spring.order.web.dto.request.OrderCreateRequest;
import sample.cafekiosk.spring.order.web.dto.response.OrderResponse;

import java.util.List;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/orders")
    public void createOrder(@RequestBody OrderCreateRequest request) {
        orderService.createOrder(request);
    }

    @GetMapping("/orders")
    public List<OrderResponse> getOrders() {
        return orderService.getAllOrders();
    }
}
