package sample.cafekiosk.spring.api.order.web.dto.response;

import sample.cafekiosk.spring.api.order.domain.Order;
import sample.cafekiosk.spring.api.order.domain.OrderStatus;
import sample.cafekiosk.spring.api.product.web.dto.ProductResponse;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
         Long id,
         OrderStatus orderStatus,
         int totalPrice,
         LocalDateTime registeredAt,
         List<ProductResponse> products
) {
    public static OrderResponse from(Order order, List<ProductResponse> productResponses) {
        return new OrderResponse(order.getId(), order.getOrderStatus(), order.getTotalPrice(), order.getCreatedAt(), productResponses);
    }
}
