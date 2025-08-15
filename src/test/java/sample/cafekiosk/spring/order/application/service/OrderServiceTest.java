package sample.cafekiosk.spring.order.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.order.domain.Order;
import sample.cafekiosk.spring.order.domain.OrderProduct;
import sample.cafekiosk.spring.order.repository.OrderRepository;
import sample.cafekiosk.spring.order.web.dto.request.OrderCreateRequest;
import sample.cafekiosk.spring.product.domain.Product;
import sample.cafekiosk.spring.product.repository.ProductRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sample.cafekiosk.spring.product.domain.ProductType.BOTTLE;
import static sample.cafekiosk.spring.product.utility.ProductUtils.*;

@Transactional
@SpringBootTest
class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    private static Object getProductPrice(OrderProduct orderProduct) {
        return orderProduct.getProduct().getPrice();
    }

    @DisplayName("주문번호 리스트를 받아서 주문을 생성하고나면 생성된 주문이 조회된다.")
    @Test
    public void should_hasSize_2_when_createOrder_called() {
        // given
        Product item1 = createProduct(BOTTLE, 1000, "001");
        Product item2 = createProduct(BOTTLE, 3000, "002");
        productRepository.saveAll(List.of(item1, item2));

        OrderCreateRequest request = new OrderCreateRequest(List.of(item1.getProductNumber(), item2.getProductNumber()));

        // when
        orderService.createOrder(request);
        
        // then
        Order createdOrder = orderRepository.findAll()
                .stream()
                .findFirst().orElseThrow();

        assertThat(createdOrder.getId()).isNotNull();
        assertThat(createdOrder.getOrderProducts())
                .hasSize(2)
                .extracting(
                        orderProduct -> orderProduct.getProduct().getProductNumber(),
                        OrderServiceTest::getProductPrice
                )
                .containsExactlyInAnyOrder(
                        tuple(item1.getProductNumber(), item1.getPrice()),
                        tuple(item2.getProductNumber(), item2.getPrice())
                );

    }
}