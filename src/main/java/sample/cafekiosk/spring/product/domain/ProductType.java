package sample.cafekiosk.spring.product.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ProductType {
    HANDMADE(""),
    BOTTLE("병음료"),
    BAKERY("베이커리"),
    ;
    private final String description;
}
