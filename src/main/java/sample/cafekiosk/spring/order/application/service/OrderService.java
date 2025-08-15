package sample.cafekiosk.spring.order.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.order.domain.Order;
import sample.cafekiosk.spring.order.domain.OrderProduct;
import sample.cafekiosk.spring.order.repository.OrderRepository;
import sample.cafekiosk.spring.order.web.dto.request.OrderCreateRequest;
import sample.cafekiosk.spring.order.web.dto.response.OrderResponse;
import sample.cafekiosk.spring.product.domain.Product;
import sample.cafekiosk.spring.product.repository.ProductRepository;
import sample.cafekiosk.spring.product.web.dto.ProductResponse;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request) {
        Order order = Order.create(LocalDateTime.now());
        List<Product> findProducts = productRepository.findAllByProductNumberIn(request.productNumbers());

        findProducts.forEach(p-> {
            OrderProduct orderProduct = OrderProduct.create(p);
            order.addOrderProduct(orderProduct);
        });

        orderRepository.save(order);

        return OrderResponse.from(order, convertToProductResponses(findProducts));
    }

    private static List<ProductResponse> convertToProductResponses(List<Product> findProducts) {
        return findProducts.stream()
                .map(ProductResponse::from)
                .toList();
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(o -> {
                    List<ProductResponse> productResponses = o.getOrderProducts().stream()
                            .map(op -> ProductResponse.from(op.getProduct()))
                            .toList();
                    return OrderResponse.from(o, productResponses);
                })
                .toList();
    }
}
