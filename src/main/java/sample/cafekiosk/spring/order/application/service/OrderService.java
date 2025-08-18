package sample.cafekiosk.spring.order.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.order.application.dto.request.OrderCreateServiceRequest;
import sample.cafekiosk.spring.order.domain.Order;
import sample.cafekiosk.spring.order.domain.OrderProduct;
import sample.cafekiosk.spring.order.repository.OrderRepository;
import sample.cafekiosk.spring.order.web.dto.response.OrderResponse;
import sample.cafekiosk.spring.product.domain.Product;
import sample.cafekiosk.spring.product.domain.ProductType;
import sample.cafekiosk.spring.product.repository.ProductRepository;
import sample.cafekiosk.spring.product.web.dto.ProductResponse;
import sample.cafekiosk.spring.stock.domain.Stock;
import sample.cafekiosk.spring.stock.repository.StockRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    @Transactional
    public OrderResponse createOrder(OrderCreateServiceRequest request, LocalDateTime registeredAt) {
        Order order = Order.create(registeredAt);

        List<Product> productWithDuplicatedNumber = getProductsConsideringDuplicatedProductNumber(request);

        decreaseStocksByProductType(productWithDuplicatedNumber);

        productWithDuplicatedNumber.forEach(product -> {
            order.addOrderProduct(OrderProduct.create(product));
        });

        orderRepository.save(order);
        return OrderResponse.from(order, convertToProductResponses(productWithDuplicatedNumber));
    }

    private void decreaseStocksByProductType(List<Product> productWithDuplicatedNumber) {
        List<Product> needToDecreaseStock = productWithDuplicatedNumber.stream()
                .filter(p -> ProductType.hasStockProperty(p.getProductType()))
                .toList();
        needToDecreaseStock.forEach(p -> {
            Stock stock = stockRepository.findByProduct(p)
                    .stream()
                    .findFirst().orElseThrow();
            stock.decreaseQuantity(1);
        });
    }

    private List<Product> getProductsConsideringDuplicatedProductNumber(OrderCreateServiceRequest request) {
        List<Product> findProducts = productRepository.findAllByProductNumberIn(request.productNumbers());

        Map<String, Product> productMap = findProducts.stream()
                .collect(Collectors.toMap(Product::getProductNumber, p -> p));

        List<Product> productWithDuplicatedNumber = request.productNumbers().stream()
                .map(productNumber -> productMap.get(productNumber))
                .toList();
        return productWithDuplicatedNumber;
    }

    private List<ProductResponse> convertToProductResponses(List<Product> findProducts) {
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
