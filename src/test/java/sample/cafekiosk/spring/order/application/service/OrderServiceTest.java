package sample.cafekiosk.spring.order.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.order.application.dto.request.OrderCreateServiceRequest;
import sample.cafekiosk.spring.order.domain.Order;
import sample.cafekiosk.spring.order.domain.OrderProduct;
import sample.cafekiosk.spring.order.repository.OrderRepository;
import sample.cafekiosk.spring.product.domain.Product;
import sample.cafekiosk.spring.product.repository.ProductRepository;
import sample.cafekiosk.spring.stock.domain.Stock;
import sample.cafekiosk.spring.stock.repository.StockRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
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
    @Autowired
    private StockRepository stockRepository;

    private static Object getProductPrice(OrderProduct orderProduct) {
        return orderProduct.getProduct().getPrice();
    }

/*
    @AfterEach
    void tearDown() {   // tear down: 각 테스트케이스가 끝난 후 실행되는 정리용 코드
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
    }
*/

    @DisplayName("주문번호 리스트를 받아서 주문을 생성하고나면 생성된 주문이 조회된다.")
    @Test
    public void should_hasSize_2_when_createOrder_called() {
        // given
        Product item1 = createProduct(BOTTLE, 1000, "001");
        Product item2 = createProduct(BOTTLE, 3000, "002");
        productRepository.saveAll(List.of(item1, item2));

        Stock stock1 = Stock.create(item1, 10);
        Stock stock2 = Stock.create(item2, 10);
        stockRepository.saveAll(List.of(stock1, stock2));

        OrderCreateServiceRequest request = new OrderCreateServiceRequest(List.of(item1.getProductNumber(), item2.getProductNumber()));

        // when
        orderService.createOrder(request, LocalDateTime.now());
        
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
        List<Stock> stocks = stockRepository.findAll();
        assertThat(stocks).hasSize(2)
                .extracting(s -> s.getProduct().getProductNumber(), s -> s.getQuantity())
                .containsExactlyInAnyOrder(
                        tuple(item1.getProductNumber(), 9),
                        tuple(item2.getProductNumber(), 9)
                );
    }
    @DisplayName("주문번호 리스트를 받아서 주문을 생성하고나면 생성된 주문이 조회된다. - 중복되는 주문번호")
    @Test
    public void should_hasSize_2_when_createOrder_called_with_duplicate_productNumber() {
        // given
        Product item1 = createProduct(BOTTLE, 1000, "001");
        Product item2 = createProduct(BOTTLE, 3000, "002");
        productRepository.saveAll(List.of(item1, item2));

        Stock stock1 = Stock.create(item1, 10);
        Stock stock2 = Stock.create(item2, 10);
        stockRepository.saveAll(List.of(stock1, stock2));

        OrderCreateServiceRequest request = new OrderCreateServiceRequest(List.of(item1.getProductNumber(), item1.getProductNumber()));

        // when
        orderService.createOrder(request, LocalDateTime.now());

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
                        tuple(item1.getProductNumber(), item1.getPrice())
                );

        List<Stock> stocks = stockRepository.findAll();
        assertThat(stocks).hasSize(2)
                .extracting(s -> s.getProduct().getProductNumber(), s -> s.getQuantity())
                .containsExactlyInAnyOrder(
                        tuple(item1.getProductNumber(), 8),
                        tuple(item2.getProductNumber(), 10)
                );
    }

    @DisplayName("재고보다 적은 개수만큼 상품을 주문한다.")
    @Test
    public void should_hasSize_1_when_createOrder_called_with_stocks_2_or_more() {
        // given
        Product item1 = createProduct(BOTTLE, 1000, "001");
        Product item2 = createProduct(BOTTLE, 3000, "002");
        productRepository.saveAll(List.of(item1, item2));

        Stock stock1 = Stock.create(item1, 10);
        Stock stock2 = Stock.create(item2, 10);
        stockRepository.saveAll(List.of(stock1, stock2));

        OrderCreateServiceRequest request = new OrderCreateServiceRequest(List.of(item1.getProductNumber(), item1.getProductNumber()));

        // when
        Long id = orderService.createOrder(request, LocalDateTime.now()).id();

        // then
        List<Order> orders = orderRepository.findAll();
        assertThat(orders).hasSize(1)
                .extracting(Order::getTotalPrice)
                .containsExactlyInAnyOrder(item1.getPrice() * 2);
        assertThat(stock1.getQuantity()).isEqualTo(8);
    }

    @DisplayName("재고와 동일한 개수만큼 주문을 생성한다.")
    @Test
    public void should_hasSize_1_when_createOrder_called_with_stocks_1() {
        // given
        Product item1 = createProduct(BOTTLE, 1000, "001");
        Product item2 = createProduct(BOTTLE, 3000, "002");
        productRepository.saveAll(List.of(item1, item2));

        Stock stock1 = Stock.create(item1, 1);
        stockRepository.saveAll(List.of(stock1));

        OrderCreateServiceRequest request = new OrderCreateServiceRequest(List.of(item1.getProductNumber()));

        // when
        Long id = orderService.createOrder(request, LocalDateTime.now()).id();

        // then
        List<Order> orders = orderRepository.findAll();
        assertThat(orders).hasSize(1)
                .extracting(Order::getTotalPrice)
                .containsExactlyInAnyOrder(item1.getPrice());
        assertThat(stock1.getQuantity()).isEqualTo(0);
    }

    @DisplayName("재고가 0개 남은 상품에 대해 1개 이상 주문을 신청하면 예외가 발생한다")
    @Test
    public void should_throw_exception_when_createOrder_called_with_stocks_0() {
        // given
        Product item1 = createProduct(BOTTLE, 1000, "001");
        Product item2 = createProduct(BOTTLE, 3000, "002");
        productRepository.saveAll(List.of(item1, item2));

        Stock stock1 = Stock.create(item1, 0);
        stockRepository.saveAll(List.of(stock1));

        OrderCreateServiceRequest request = new OrderCreateServiceRequest(List.of(item1.getProductNumber()));

        // when & then
        assertThatThrownBy(() -> orderService.createOrder(request, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고가 부족합니다");
    }

    @DisplayName("재고 감소 시나리오")
    @TestFactory
    public Collection<DynamicTest> stockDecreaseScenario() {
        // given
        Product item1 = createProduct(BOTTLE, 1000, "001");
        Product item2 = createProduct(BOTTLE, 3000, "002");
        productRepository.saveAll(List.of(item1, item2));

        Stock stock = Stock.create(item1, 1);
        stockRepository.saveAll(List.of(stock));

        return List.of(
                DynamicTest.dynamicTest("재고와 동일한 개수만큼 감소가 가능하다.", () -> {
                    // given
                    int quantity = 1;

                    // when
                    stock.decreaseQuantity(quantity);

                    // then
                    assertThat(stock.getQuantity()).isEqualTo(0);
                }),
                DynamicTest.dynamicTest("재고보다 많은 개수만큼 감소시키면 예외가 발생한다.", () -> {
                    // given
                    int quantity = 1;

                    // when
                    assertThatThrownBy(() -> stock.decreaseQuantity(quantity))
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessage("재고가 부족합니다");
                })
        );
    }
}