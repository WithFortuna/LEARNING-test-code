package sample.cafekiosk.spring.stock.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.product.domain.Product;
import sample.cafekiosk.spring.product.repository.ProductRepository;
import sample.cafekiosk.spring.product.web.dto.ProductResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static sample.cafekiosk.spring.product.utility.ProductUtils.*;

@Transactional
@SpringBootTest
class StockTest {
    @Autowired
    ProductRepository productRepository;

    @DisplayName("남은 재고가 요청한 수량보다 크면 재고를 차감한다.")
    @Test
    public void should_return_9_when_decrease_1() {
        // given
        Product item1 = createProduct_BOTTLE_SELLING();
        productRepository.saveAll(List.of(item1));
        Stock stock = Stock.create(item1, 10);

        // when
        stock.decreaseQuantity(9);

        // then
        assertThat(stock.getQuantity()).isEqualTo(1);
    }

    @DisplayName("남은 재고가 요청한 수량보다 부족하면 예외가 발생한다.")
    @Test
    public void should_throw_exception_when_decreaseQuantity_with_quantity_over() {
        // given
        Product item1 = createProduct_BOTTLE_SELLING();
        productRepository.saveAll(List.of(item1));
        Stock stock = Stock.create(item1, 10);

        // when & then
        assertThatThrownBy(() -> stock.decreaseQuantity(11))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고가 부족합니다");
    }
}