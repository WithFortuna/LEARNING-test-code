package sample.cafekiosk.spring.api.order.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.spring.api.product.domain.Product;
import sample.cafekiosk.spring.api.product.utility.ProductUtils;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static sample.cafekiosk.spring.api.product.domain.ProductType.BOTTLE;


class OrderTest {
    @DisplayName("상품을 추가하면 그 가격만큼 totalPrice에 더해준다")
    @Test
    public void should_return_3000_when_add_products() {
        // given
        Product item1 = ProductUtils.createProduct(BOTTLE, 1000, "001");
        Product item2 = ProductUtils.createProduct(BOTTLE, 2000, "002");

        Order order = Order.create(LocalDateTime.now());
        // when
        order.addOrderProduct(OrderProduct.create(item1));
        order.addOrderProduct(OrderProduct.create(item2));

        // then
        assertThat(order.getTotalPrice()).isEqualTo(3000);
    }

    @DisplayName("주문을 생성하면 초기 상태는 INIT이다.")
    @Test
    public void should_status_INIT_when_createOrder() {
        // given
        Order order = Order.create(LocalDateTime.now());

        // when

        // then
        Assertions.assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.INIT);

    }

}