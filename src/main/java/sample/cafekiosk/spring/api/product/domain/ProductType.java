package sample.cafekiosk.spring.api.product.domain;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public enum ProductType {
    HANDMADE("수작업"),
    BOTTLE("병음료"),
    BAKERY("베이커리"),
    ;
    private final String description;

    public static boolean hasStockProperty(ProductType productType) {
        return List.of(BOTTLE, BAKERY).contains(productType);
    }
}
