package sample.cafekiosk.spring.product.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTypeTest {
    @DisplayName("상품타입이 재고속성을 가지고 있다면 true를 반환한다.")
    @Test
    public void should_return_true_when_ProductType_is_valid() {
        // given
        ProductType bottle = ProductType.BOTTLE;
        ProductType bakery = ProductType.BAKERY;
        ProductType handmade = ProductType.HANDMADE;

        // when
        boolean isBottleInStock = ProductType.hasStockProperty(bottle);
        boolean isBakeryInStock = ProductType.hasStockProperty(bakery);
        boolean isHandmadeInStock = ProductType.hasStockProperty(handmade);

        // then
        assertThat(isBottleInStock).isTrue();
        assertThat(isBakeryInStock).isTrue();
        assertThat(isHandmadeInStock).isFalse();
    }

}