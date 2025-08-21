package sample.cafekiosk.spring.order.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import sample.cafekiosk.spring.IntegrationTestSupport;
import sample.cafekiosk.spring.mail.application.MailSender;
import sample.cafekiosk.spring.order.application.dto.request.OrderCreateServiceRequest;
import sample.cafekiosk.spring.product.domain.Product;
import sample.cafekiosk.spring.product.repository.ProductRepository;
import sample.cafekiosk.spring.stock.domain.Stock;
import sample.cafekiosk.spring.stock.repository.StockRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static sample.cafekiosk.spring.product.domain.ProductType.BOTTLE;
import static sample.cafekiosk.spring.product.utility.ProductUtils.createProduct;

class OrderStatisticsServiceTest extends IntegrationTestSupport {
    @Autowired
    private OrderStatisticsService orderStatisticsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StockRepository stockRepository;
    @MockitoBean
    private MailSender mailSender;

    @DisplayName("특정 날짜를 입력받으면 해당 날짜의 합계 매출을 반환한다.")
    @Test
    public void should_return_totalSales_when_sendOrderStatisticsToMail_with_given_date() {
        // given
        Product item1 = createProduct(BOTTLE, 1000, "001");
        Product item2 = createProduct(BOTTLE, 3000, "002");
        productRepository.saveAll(List.of(item1, item2));

        Stock stock1 = Stock.create(item1, 10);
        Stock stock2 = Stock.create(item2, 10);
        stockRepository.saveAll(List.of(stock1, stock2));

        OrderCreateServiceRequest request1 = new OrderCreateServiceRequest(List.of(item1.getProductNumber(), item2.getProductNumber()));
        OrderCreateServiceRequest request2 = new OrderCreateServiceRequest(List.of(item1.getProductNumber()));

        LocalDateTime registeredAt1 = LocalDateTime.of(20205, 1, 1, 12, 0, 0);
        orderService.createOrder(request1, registeredAt1);

        // stubbing
        Mockito.when(mailSender.sendMail(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(true);

        // when
        orderStatisticsService.sendOrderStatisticsToMail(registeredAt1.toLocalDate(), "abc@naver.com");
    }
}