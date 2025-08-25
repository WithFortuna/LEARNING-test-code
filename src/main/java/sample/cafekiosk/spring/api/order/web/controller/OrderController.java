package sample.cafekiosk.spring.api.order.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sample.cafekiosk.spring.api.order.application.service.OrderService;
import sample.cafekiosk.spring.api.order.web.dto.request.OrderCreateRequest;
import sample.cafekiosk.spring.api.order.web.dto.response.OrderResponse;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/orders")
    public OrderResponse createOrder(@RequestBody @Valid OrderCreateRequest request) {
        return orderService.createOrder(request.toServiceRequest(), LocalDateTime.now());
    }

    @GetMapping("/orders")
    public List<OrderResponse> getOrders() {
        return orderService.getAllOrders();
    }
}
