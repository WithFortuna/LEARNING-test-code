package sample.cafekiosk.spring.product.domain;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public enum ProductSellingType {
    SELLING("판매중"),
    NOT_SELLING("판매중지"),
    HOLDING("판매 대기"),
    ;
    private final String description;

    public static List<ProductSellingType> forDisplay() {
        return List.of(SELLING, HOLDING);
    }
}
