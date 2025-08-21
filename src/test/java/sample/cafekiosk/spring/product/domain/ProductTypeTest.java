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
        List<ProductType> validProductTypes = List.of(BOTTLE, BAKERY);
        List<ProductType> invalidProductTypes = List.of(HANDMADE);
       
        // when
        List<Boolean> resultsForInvalidProductType = invalidProductTypes.stream()
                .map(ProductType::hasStockProperty)
                .toList();
        List<Boolean> resultsForValidProductType = validProductTypes.stream()
                .map(ProductType::hasStockProperty)
                .toList();

        // then
        assertThat(resultsForValidProductType)
                .hasSize(2)
                .containsOnly(true);
        assertThat(resultsForInvalidProductType)
                .hasSize(1)
                .containsOnly(false);
    }

    /// @Paramterized - 소스1: @CsvSource({"",""})
    @DisplayName("상품타입이 재고속성을 가지고 있다면 true를 반환한다.")
    @CsvSource({"BOTTLE, true", "BAKERY, true", "HANDMADE, false"})
    @ParameterizedTest(name = "{index} => productType={0}, expectedResult={1}")
    public void should_return_true_when_ProductType_is_valid_used_parameterizedTest_csvSource(
            ProductType productType, boolean expectedResult) {
        // when
        boolean result = hasStockProperty(productType);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @DisplayName("상품타입이 재고속성을 가지고 있다면 true를 반환한다.")
    @MethodSource("provideProductTypeAndExpectedResult")
    @ParameterizedTest
    public void should_return_true_when_ProductType_is_valid_used_parameterizedTest_methodSource(
            ProductType productType, boolean expectedResult
    ) {
        // when
        boolean result = hasStockProperty(productType);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> provideProductTypeAndExpectedResult() {
        return Stream.of(
                Arguments.of(BOTTLE, true),
                Arguments.of(BAKERY, true),
                Arguments.of(HANDMADE, false)
        );
    }
}